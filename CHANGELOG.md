# Changelog

Tất cả thay đổi quan trọng của dự án BatTu được ghi lại ở đây.

---

## [2026-03-23] - Bazi Engine Fix (Phase 04) ✅

### ✅ Added
- **PromptBuilder.kt**
  - Cấu trúc JSON mới truyền cho AI: Bao gồm danh sách **Thần Sát**, **Tương Tác** (Hợp/Xung/Hình/Hại) và **Đại Vận**.
  - Nâng cấp **Tàng Can**: Xuất dưới dạng mảng Object kèm `%` trọng số và loại khí (Bản/Trung/Dư) để AI hiểu sâu hơn.
  - Bổ sung **Trường Sinh (Life Stage)** cho từng trụ.
  - Tích hợp **Mùa sinh** và **Sức mạnh Nhật Chủ** vào block Ngũ hành.

### ✅ Tests
- **PromptBuilderTest.kt**
  - Unit test kiểm tra tính toàn vẹn và cấu trúc của chuỗi JSON prompt mới.
  - Đã fix lỗi Stub! khi chạy test JSON trên JVM bằng cách thêm `org.json:json` dependency.

---

## [2026-03-23] - Bazi Engine Fix (Phase 03) ✅

### ✅ Added
- **BaZiConstants.kt**
  - Bảng tra cứu **Thần Sát (Shen Sha)**: Thiên Ất Quý Nhân, Lộc Tồn, Kình Dương, Dịch Mã, Hoa Cái.
  - Bảng tra cứu **Tương Tác Địa Chi**: Lục Hợp, Lục Xung, Lục Hại, Tam Hợp, Tam Hình, Tự Hình.
- **BaZiLogic.kt**
  - Logic tính **Thần Sát** dựa trên trụ Ngày và trụ Năm.
  - Logic quét **Tương Tác** (Hợp/Xung/Hình/Hại) giữa các trụ trong lá số.
  - Thuật toán tính **Đại Vận (Luck Pillars)**: Xác định chiều (Thuận/Nghịch), Tuổi khởi vận (dựa trên Tiết Khí) và chu kỳ 10 năm.
- **Models Update**
  - Thêm `ShenSha`, `Interaction`, `InteractionType`, `LuckPillar`.
  - Cập nhật `BaZiData` chứa danh sách kết quả tính toán chi tiết.

---

## [2026-03-23] - Bazi Engine Fix (Phase 02)

### ✅ Added
- **BaZiConstants.kt**
  - Bảng tra cứu **Trường Sinh (12 Life Stages)** cho 10 Thiên Can áp dụng quy tắc **Âm nghịch - Dương thuận**.
  - Bảng tra cứu **Mùa sinh (Season)** và sức mạnh Nhật Chủ (Vượng/Tướng/Hưu/Tù/Tử) theo mùa.
  - Helper functions: `getLifeStage`, `getSeason`, `getDayMasterStrength`.

- **Models Update**
  - `Pillar`: Thêm trường `lifeStage` hiển thị trạng thái của Nhật chủ tại mỗi trụ.
  - `BaZiData`: Thêm trường `season` và `dayMasterStrength`.

### ✅ Tests
- **BaZiConstantsTest.kt**
  - Unit tests cho quy tắc Trường Sinh thuận/nghịch (TC-08).
  - Unit tests cho logic Mùa sinh và sức mạnh Nhật chủ (TC-09).

### 🔧 Changed
- **BaZiLogic.kt**
  - Nâng cấp **Element Balance**: Điểm số được tính toán dựa trên trọng số Tàng Can (Bản/Trung/Dư khí) và cộng thêm bonus điểm từ Nạp Âm (+10).
  - Tích hợp tính toán Mùa sinh và gán trạng thái Trường sinh cho từng trụ.

---

## [2026-03-23] - Bazi Engine Fix (Phase 01)

### ✅ Added
- **BaZiConstants.kt**
  - Bảng tra cứu **60 Giáp Tý Nạp Âm** chính xác (Lục Thập Hoa Giáp).
  - Bảng **Tàng Can của 12 Địa Chi** với trọng số phần trăm (Bản khí 60%, Trung khí 30%, Dư khí 10%).
  - Logic tính **Thập Thần (Ten Gods)** dựa trên Nhật Chủ và Bản khí của Địa Chi.
  - Helper functions cho quan hệ Sinh/Khắc/Âm/Dương và Ngũ Hành.

- **Models Update**
  - Thêm data class: `NapAm`, `HiddenStem`, `Element`.
  - Cập nhật `Pillar` để bao gồm Nạp Âm và danh sách Tàng Can chi tiết.

### ✅ Tests
- **BaZiConstantsTest.kt**
  - Unit tests tra cứu Nạp Âm chuẩn (TC-01).
  - Unit tests trọng số Tàng Can (TC-02).
  - Unit tests tính Thập Thần (TC-04, TC-05).

### 🔧 Changed
- **BaZiLogic.kt**
  - Thay thế logic tính toán cũ bằng `BaZiConstants`.
  - Sửa logic tính toán **Element Balance** dựa trên trọng số Tàng Can chính xác.
  - Tự động gán Nạp Âm cho các trụ.

- **UI Components**
  - `PillarCard`: Hiển thị Tàng Can kèm phần trăm ảnh hưởng (%).
  - `PromptBuilder`: Cập nhật JSON prompt gửi AI bao gồm Nạp Âm và Tàng Can chi tiết.

### 🐛 Fixed
- Lỗi map Nạp Âm đơn giản (VD: Nhâm Thân map thành Thủy Kim).
- Lỗi lấy sai Thập Thần Địa Chi (VD: Thìn lấy Trung khí thay vì Bản khí).

---

## [2026-03-23] - Phase 04 Completion

### ✅ Added
- **AI Integration với Gemini**
  - `GeminiClient.kt`: Multi-API key rotation với model fallback
  - `PromptBuilder.kt`: JSON prompt builder chuyên sâu cho Bát Tự
  - Streaming response với `Flow<String>`
  - Smart error handling (quota, network, invalid keys)

- **ResultScreen**
  - Hiển thị kết quả AI với streaming text
  - MarkdownText component để render formatted response
  - Copy to clipboard functionality
  - Share button (TODO: implement share intent)

- **SettingsScreen**
  - Quản lý multiple API keys
  - Test API key functionality
  - Model selection dropdown
  - DataStore persistence cho settings

- **Navigation Updates**
  - Integrated ResultScreen vào NavHost
  - Integrated SettingsScreen vào NavHost
  - Settings icon button trong InputScreen TopAppBar
  - "Gửi AI" button trong ChartScreen trigger AI generation

### 🔧 Changed
- **BatTuViewModel**
  - Added `generateAIInterpretation()` function
  - Added `testApiKey()` function
  - Added StateFlows: `aiResult`, `isGenerating`
  - Fixed property names to match `InputScreenState` structure

- **Dependencies**
  - Added `androidx.datastore:datastore-preferences:1.0.0`

### 🐛 Fixed
- **PromptBuilder.kt**
  - Fixed element names: `elementBalance["Kim"]` thay vì `elementBalance.kim`
  - Fixed TenGods property names: `yearStemGod` thay vì `yearStem`
  - Removed non-existent `napAm` property
  - Updated `buildJsonPrompt` signature: `(name, gender, baZiData)` thay vì `(data, userInput)`

- **GeminiClient.kt**
  - Fixed function calls to `PromptBuilder.buildJsonPrompt` với correct parameters
  - Updated to pass `name`, `gender`, `baZiData` separately

- **BatTuViewModel.kt**
  - Fixed `generateReadingStream` call: pass `BaZiData` và `UserInput` objects thay vì string
  - Fixed `testConnection` return type handling: `Result<String>` thay vì `Boolean`
  - Fixed property names: `birthDay`, `birthMonth`, `birthYear`, `birthHour`

- **ResultScreen.kt**
  - Removed non-existent `Icons.Default.ContentCopy` và `Icons.Default.Share`
  - Used emoji text (📋, 🔗) thay vì icons
  - Implemented direct clipboard copy với `LocalClipboardManager`

- **SettingsScreen.kt**
  - Replaced deprecated `Divider` với `HorizontalDivider`

---

## [2026-03-23] - Phase 03 Completion

### ✅ Added
- **ChartScreen**
  - Hiển thị 4 trụ (Year, Month, Day, Hour) dạng grid
  - `PillarCard` component cho mỗi trụ
  - `ElementBalanceChart` component (Canvas-based bar chart)
  - `ElementBadge` component
  - Action buttons: Copy JSON, Gửi AI, Lưu

- **InputScreen**
  - Form nhập thông tin: Tên, Giới tính, Ngày sinh
  - Toggle Dương lịch / Âm lịch
  - Date/Time pickers
  - Validation logic

- **UI Theme**
  - Material 3 color scheme
  - Typography definitions
  - Dark/Light theme support

- **Navigation**
  - Jetpack Navigation Compose
  - Routes: input, chart, result, settings

### 🔧 Changed
- **BatTuViewModel**
  - Added `calculateBaZi()` function
  - Added StateFlows: `inputState`, `baZiData`, `isLoading`, `errorMessage`

---

## [2026-03-23] - Phase 02 Completion

### ✅ Added
- **Core Bát Tự Logic**
  - `BaZiLogic.kt`: Tính toán Tứ Trụ, Thập Thần, Ngũ Hành
  - `Constants.kt`: Thiên Can, Địa Chi, Ngũ Hành mappings
  - `LunarConverter.kt`: Solar ↔ Lunar conversion
  - `LunarDateUtil.kt`: Julian Day calculations

- **Data Models**
  - `Models.kt`: `BaZiData`, `Pillar`, `TenGods`, `UserInput`, `Gender`

- **Assets**
  - `solar-term.json`: 24 Tiết Khí data (278KB)

### ✅ Tests
- `BaZiLogicTest.kt`: Unit tests cho core logic

### 🐛 Fixed
- JDK 21 jlink error: Set `org.gradle.java.home` to JDK 17
- Gradle cache issues: `./gradlew --stop` và rebuild

---

## [2026-03-23] - Phase 01 Completion

### ✅ Added
- **Project Setup**
  - Android project với Kotlin 2.0.20
  - Jetpack Compose 1.7.5
  - Material 3 1.3.1
  - Gradle 9.2.1

- **Dependencies**
  - Compose BOM
  - Navigation Compose
  - Lifecycle ViewModel
  - Kotlinx Serialization
  - Google Generative AI SDK

- **Project Structure**
  - Package structure: `core/`, `data/`, `ai/`, `ui/`
  - Build configuration
  - ProGuard rules

---

## Pending (Phase 05)

### 🔜 To Be Added
- Room Database for history storage
- HistoryScreen UI
- Share functionality implementation
- App icon & splash screen
- Bottom Navigation Bar
- Dark/Light theme toggle in Settings

---

**Format:** [YYYY-MM-DD] - Description
**Categories:** Added, Changed, Deprecated, Removed, Fixed, Security
