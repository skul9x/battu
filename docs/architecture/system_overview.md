# BatTu Project - Architecture Overview

**Last Updated:** 2026-03-23

## 📱 Project Overview

**BatTu** (Bát Tự - Tử Bình Luận Giải) là ứng dụng Android tính toán và luận giải lá số Bát Tự (Tứ Trụ) bằng AI Gemini theo phương pháp Tử Bình Chân Thuyên.

- **Package:** `com.skul9x.battu`
- **Platform:** Android (Min SDK 24, Target SDK 34)
- **Language:** Kotlin 2.0.20
- **UI Framework:** Jetpack Compose + Material 3

---

## 🏗️ Architecture Pattern

**MVVM (Model-View-ViewModel)** với Clean Architecture principles

```
┌─────────────────────────────────────────────────┐
│              UI Layer (Compose)                 │
│  InputScreen → ChartScreen → ResultScreen       │
│              SettingsScreen                     │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│           ViewModel Layer                       │
│  BatTuViewModel (StateFlow + Coroutines)        │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│          Core Logic Layer                       │
│  BaZiLogic, LunarConverter, Constants           │
└─────────────────────────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│            AI Layer                             │
│  GeminiClient (Multi-key + Streaming)           │
│  PromptBuilder (JSON prompt generation)         │
└─────────────────────────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│          Data Layer                             │
│  Models, DataStore (Settings)                   │
│  Room Database (History - Phase 05)             │
└─────────────────────────────────────────────────┘
```

---

## 📦 Package Structure

```
com.skul9x.battu/
├── core/                    # Core Bát Tự calculation logic
│   ├── BaZiLogic.kt        # Main calculation engine (251 LOC)
│   ├── BaZiConstants.kt    # Nạp Âm, Tàng Can, Thập Thần (lookup tables)
│   ├── Constants.kt        # Cơ bản (Thiên Can, Địa Chi) - Phasing out
│   ├── LunarConverter.kt   # Solar ↔ Lunar conversion (80 LOC)
│   └── LunarDateUtil.kt    # Julian Day calculations (169 LOC)
│
├── data/                    # Data models and storage
│   ├── Models.kt           # BaZiData, Pillar, TenGods, UserInput
│   └── local/              # Room Database (Phase 05)
│       ├── HistoryEntity.kt
│       ├── HistoryDao.kt
│       └── HistoryDatabase.kt
│
├── ai/                      # AI Integration
│   ├── GeminiClient.kt     # Multi-key rotation + streaming (1469 LOC)
│   └── PromptBuilder.kt    # JSON prompt builder for Bát Tự
│
├── ui/                      # UI Layer
│   ├── screens/
│   │   ├── InputScreen.kt      # Nhập thông tin sinh
│   │   ├── ChartScreen.kt      # Hiển thị lá số
│   │   ├── ResultScreen.kt     # Kết quả AI luận giải
│   │   └── SettingsScreen.kt   # Quản lý API keys
│   │
│   ├── components/
│   │   ├── PillarCard.kt           # Card hiển thị 1 trụ
│   │   ├── ElementBalanceChart.kt  # Biểu đồ Ngũ Hành
│   │   ├── ElementBadge.kt         # Badge cho ngũ hành
│   │   └── MarkdownText.kt         # Render markdown AI response
│   │
│   ├── viewmodel/
│   │   └── BatTuViewModel.kt   # State management
│   │
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
└── MainActivity.kt          # Single Activity + Navigation
```

---

## 🔄 Data Flow

### 1. Input Flow
```
User Input (InputScreen)
    ↓
BatTuViewModel.updateInput()
    ↓
StateFlow<InputScreenState>
    ↓
UI updates reactively
```

### 2. Calculation Flow
```
User clicks "Tính Lá Số"
    ↓
BatTuViewModel.calculateBaZi()
    ↓
BaZiLogic.calculateBaZi(userInput)
    ↓
LunarConverter (if needed)
    ↓
BaZiData (4 Pillars + TenGods + Elements)
    ↓
StateFlow<BaZiData?>
    ↓
Navigate to ChartScreen
```

### 3. AI Interpretation Flow
```
User clicks "Gửi AI"
    ↓
BatTuViewModel.generateAIInterpretation()
    ↓
PromptBuilder.buildJsonPrompt(name, gender, baZiData)
    ↓
GeminiClient.generateReadingStream(data, userInput)
    ↓
Flow<String> (streaming chunks)
    ↓
Collect in ViewModel
    ↓
StateFlow<String> (accumulated result)
    ↓
ResultScreen displays with MarkdownText
```

---

## 🧠 Core Components

### BaZiLogic.kt
**Trách nhiệm:** Tính toán Tứ Trụ (Year, Month, Day, Hour)

**Key Functions:**
- `calculateBaZi(userInput: UserInput): BaZiData`
- `createPillar(can: String, chi: String): Pillar` (Uses BaZiConstants)
- `calculateElementBalance(pillars: List<Pillar>): Map<String, Int>` (Refined with weights)

**Dependencies:**
- `BaZiConstants` (Na Yin, Hidden Stems, Ten Gods accurate logic)
- `Constants` (Basic Thiên Can, Địa Chi lists)
- `LunarConverter` (for lunar date conversion)

### GeminiClient.kt
**Trách nhiệm:** Tích hợp Gemini AI với multi-key rotation

**Key Features:**
- Multi-API key rotation (thử tất cả keys trước khi fail)
- Model priority fallback: `gemini-3-flash` → `gemini-2.5-flash` → `gemini-2.5-flash-lite`
- Streaming response với `Flow<String>`
- Smart error handling (quota exceeded, network errors)

**Key Functions:**
- `generateReadingStream(data: BaZiData, userInput: UserInput): Flow<String>`
- `testConnection(apiKey: String): Result<String>`
- `getPromptForCopy(data: BaZiData, userInput: UserInput): String`

### PromptBuilder.kt
**Trách nhiệm:** Tạo JSON prompt chuyên sâu cho Bát Tự

**Prompt Structure:**
```json
{
  "role": "Chuyên gia Bát Tự Tử Bình",
  "methodology": ["Tử Bình Chân Thuyên", "Trích Thiên Tùy", ...],
  "user_info": {...},
  "chart_data": {
    "pillars": [...],
    "ten_gods": {...},
    "element_balance": {...}
  },
  "analysis_pipeline": [...],
  "output_format": {...}
}
```

### BatTuViewModel.kt
**Trách nhiệm:** State management và business logic orchestration

**StateFlows:**
- `inputState: StateFlow<InputScreenState>`
- `baZiData: StateFlow<BaZiData?>`
- `aiResult: StateFlow<String>`
- `isLoading: StateFlow<Boolean>`
- `errorMessage: StateFlow<String?>`

---

## 🎨 UI Screens

### InputScreen
- Nhập tên, giới tính
- Chọn Dương lịch / Âm lịch
- Nhập ngày/tháng/năm/giờ sinh
- Button "Tính Lá Số"

### ChartScreen
- Hiển thị 4 trụ (Year, Month, Day, Hour) dạng grid
- Mỗi trụ: Thiên Can, Địa Chi, Tàng Can, Thập Thần
- Biểu đồ Ngũ Hành (bar chart)
- Buttons: Copy JSON, Gửi AI, Lưu

### ResultScreen
- Streaming AI response với MarkdownText
- Loading indicator khi đang generate
- Buttons: Copy (📋), Share (🔗)

### SettingsScreen
- Quản lý API keys (add, remove, test)
- Chọn AI model
- DataStore persistence

---

## 🔧 Key Technologies

### Jetpack Compose
- Declarative UI
- Material 3 theming
- Navigation Compose

### Kotlin Coroutines
- Async operations
- Flow for streaming
- StateFlow for reactive state

### DataStore
- Persistent settings storage
- API keys management

### Room Database (Phase 05)
- History storage
- Offline access to past charts

---

## 📊 Current Status

**Completed Phases:**
- ✅ Phase 01: Project Setup
- ✅ Phase 02: Core Bát Tự Logic
- ✅ Phase 03: UI Input & Chart Display
- ✅ Phase 04: AI Integration (Gemini)
- ✅ **Phase 01-06 (Fix): Accuracy Overhaul (Shen Sha, Life Stages, Harmonies, Luck Pillars)**

**In Progress:**
- 🔄 Phase 05 (New): History & Polish (Room DB integration is core, Syncing UI)

**Overall Progress:** 90% (Precision Engine 100% Completed)

---

## 🚀 Next Steps

1. Implement Room Database for history storage
2. Create HistoryScreen UI
3. Add Share functionality in ResultScreen
4. Design app icon & splash screen
5. Add Bottom Navigation Bar
6. Final testing & polish
