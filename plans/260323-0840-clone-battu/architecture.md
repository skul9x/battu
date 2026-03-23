# Architecture Analysis

## Source Architecture (TuViAndroid-BatTu)

### Pattern: Clean Architecture + MVVM

```
┌─────────────────────────────────────────┐
│           UI Layer (Compose)            │
│  - Screens (Input, Result, Settings)   │
│  - Components (Cards, Buttons, etc.)    │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         ViewModel Layer (MVVM)          │
│  - TuViViewModel (State Management)     │
│  - UI State & Events                    │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│          Data Layer (Repository)        │
│  - HistoryRepository                    │
│  - SettingsDataStore                    │
│  - HistoryDatabase (Room)               │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         Core Layer (Business Logic)     │
│  - BaZiLogic.kt ← TARGET                │
│  - TuViLogic.kt                         │
│  - LunarConverter.kt ← TARGET           │
│  - Constants.kt ← TARGET                │
│  - GeminiClient.kt                      │
└─────────────────────────────────────────┘
```

### Key Characteristics:
- **Separation of Concerns**: Clear layer boundaries
- **Android-Specific**: Tightly coupled to Android SDK
- **Stateful**: ViewModel manages UI state
- **Database-Backed**: Room for persistence

---

## Extractable Core (Pure Kotlin)

### What Can Be Extracted:

```
┌─────────────────────────────────────────┐
│         Core Calculation Engine         │
│                                         │
│  ┌───────────────────────────────────┐  │
│  │      BaZiLogic.kt (251 LOC)      │  │
│  │  - calculateBaZi()                │  │
│  │  - TST correction                 │  │
│  │  - Pillar calculations            │  │
│  │  - Element balance                │  │
│  └───────────────────────────────────┘  │
│                                         │
│  ┌───────────────────────────────────┐  │
│  │    Constants.kt (~200 LOC)        │  │
│  │  - Can/Chi arrays                 │  │
│  │  - Element mappings               │  │
│  │  - Ten Gods logic                 │  │
│  │  - Tàng Can data                  │  │
│  └───────────────────────────────────┘  │
│                                         │
│  ┌───────────────────────────────────┐  │
│  │   LunarConverter.kt (80 LOC)      │  │
│  │  - Solar-Lunar conversion         │  │
│  │  - Can Chi helpers                │  │
│  └───────────────────────────────────┘  │
│                                         │
│  ┌───────────────────────────────────┐  │
│  │   LunarDateUtil.kt (169 LOC)      │  │
│  │  - JDN calculation                │  │
│  │  - Lunar table lookup             │  │
│  └───────────────────────────────────┘  │
│                                         │
│  ┌───────────────────────────────────┐  │
│  │    Models.kt (~50 LOC)            │  │
│  │  - UserInput                      │  │
│  │  - Pillar                         │  │
│  │  - BaZiData                       │  │
│  │  - TenGods                        │  │
│  └───────────────────────────────────┘  │
│                                         │
│  ┌───────────────────────────────────┐  │
│  │   solar-term.json (278KB)         │  │
│  │  - 24 Solar Terms data            │  │
│  │  - 1900-2100 range                │  │
│  └───────────────────────────────────┘  │
└─────────────────────────────────────────┘
```

### Dependencies:
- ✅ Kotlin Standard Library (java.util.*, org.json.*)
- ✅ No Android SDK dependencies
- ✅ Pure calculation functions
- ✅ Stateless design

---

## Target Architecture (New BatTu App)

### Requirements:
1. Bát Tự calculation (cloned from source)
2. AI interpretation (new feature)
3. Web/Desktop/Mobile support (TBD)
4. Simple UI for input/output

### Proposed Architecture Options:

#### Option A: Web Application (Recommended)

```
┌─────────────────────────────────────────┐
│         Frontend (Browser)              │
│  - HTML/CSS/JavaScript                  │
│  - React/Vue/Svelte (optional)          │
│  - Input form + Result display          │
└──────────────────┬──────────────────────┘
                   │ HTTP/REST
┌──────────────────▼──────────────────────┐
│         Backend (Server)                │
│  - Node.js / Python / Kotlin JVM        │
│  - REST API endpoints                   │
│  - AI integration (OpenAI/Gemini)       │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      Bát Tự Calculation Engine          │
│  - Ported from Kotlin source            │
│  - Stateless functions                  │
│  - JSON input/output                    │
└─────────────────────────────────────────┘
```

**Pros**:
- Cross-platform (works everywhere)
- Easy deployment
- No installation required
- Simple architecture

**Cons**:
- Requires internet connection
- Server hosting costs

---

#### Option B: Desktop Application

```
┌─────────────────────────────────────────┐
│         Desktop UI                      │
│  - Electron / Tauri / JavaFX            │
│  - Native look and feel                 │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      Bát Tự Calculation Engine          │
│  - Embedded in application              │
│  - Direct function calls                │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         AI Client                       │
│  - HTTP calls to AI API                 │
│  - API key management                   │
└─────────────────────────────────────────┘
```

**Pros**:
- Offline calculation
- Fast performance
- No server needed

**Cons**:
- Platform-specific builds
- Installation required
- AI still needs internet

---

#### Option C: Mobile Application (Android)

```
┌─────────────────────────────────────────┐
│         Android UI (Compose)            │
│  - Reuse existing UI patterns           │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      Bát Tự Calculation Engine          │
│  - Direct clone (minimal changes)       │
│  - Same Kotlin code                     │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         AI Client                       │
│  - Gemini SDK or HTTP API               │
└─────────────────────────────────────────┘
```

**Pros**:
- Minimal code changes
- Reuse existing patterns
- Native performance

**Cons**:
- Android-only
- Requires Play Store or APK distribution

---

## Recommended Approach

### Phase 1: Extract Core Engine
1. Copy core calculation files
2. Remove Android dependencies
3. Create standalone Kotlin module
4. Add unit tests

### Phase 2: Choose Target Platform
**User Decision Required**:
- Web app (HTML + Backend)?
- Desktop app (Electron/Tauri)?
- Mobile app (Android/iOS)?
- CLI tool (Terminal)?

### Phase 3: Build Application
- Implement UI layer
- Integrate AI service
- Add data persistence (optional)
- Deploy/distribute

---

## Data Flow

### Input → Calculation → Output → AI

```
User Input
  ↓
┌─────────────────────┐
│ Birth Date/Time     │
│ - Solar date        │
│ - Hour (0-23)       │
│ - Longitude         │
│ - Day boundary      │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│ BaZi Calculation    │
│ - TST correction    │
│ - 4 Pillars         │
│ - Ten Gods          │
│ - Element balance   │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│ BaZiData Result     │
│ - JSON format       │
│ - Structured data   │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│ AI Interpretation   │
│ - Send to AI API    │
│ - Receive analysis  │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│ Display to User     │
│ - Chart + Analysis  │
└─────────────────────┘
```

---

## Coupling Analysis

### Tight Coupling (Cannot Separate):
- BaZiLogic ↔ Constants (data dependencies)
- BaZiLogic ↔ solar-term.json (required data)
- BaZiLogic ↔ LunarConverter (date conversion)
- LunarConverter ↔ LunarDateUtil (JDN calculation)

### Loose Coupling (Can Separate):
- Core Engine ↔ UI (any UI can use engine)
- Core Engine ↔ AI (any AI service can be used)
- Core Engine ↔ Database (optional persistence)

---

## Migration Strategy

### Direct Rewrite (Recommended)
- Copy core files to new project
- Minimal modifications
- Keep same algorithms
- Change only platform-specific code

### Advantages:
- Preserves proven logic
- Reduces bugs
- Faster development
- Easy to verify correctness

### Changes Required:
- Remove Android imports
- Adjust JSON parsing (if needed)
- Update file I/O (for solar-term.json)
- Add platform-specific UI
