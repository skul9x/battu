# Changelog

Tất cả thay đổi quan trọng của dự án BatTu được ghi lại ở đây.

---

## [2026-03-26] - Bazi Accuracy Audit & Bug Fix 🛠️
- **Bug Fix:** Sửa lỗi đảo ngược thứ tự Tàng Can (Hidden Stems) của Chi Tỵ (巳) trong `BaZiConstants.kt`.
  - Đã chuyển **Canh Kim** về đúng vị trí **Trung Khí (30%)** và **Mậu Thổ** về **Dư Khí (10%)**.
  - Lý do: Tỵ là Trường Sinh của Canh Kim, lực lượng Kim tại đây mạnh hơn Thổ. Xác nhận theo *Tam Mệnh Thông Hội* và *Uyên Hải Tử Bình*.
- **Audit:** Rà soát thuật toán tính `element_balance` trong `BaZiLogic.kt`.
  - Xác minh phép toán hiện tại (Can 40, Chi 60, Nạp Âm 10) là chính xác về mặt số học (110đ/trụ).
  - Nhận diện hạn chế: Hệ thống hiện tại coi 4 trụ có trọng số bằng nhau, chưa tính hệ số **Lệnh Tháng (Nguyệt Lệnh)** và chưa trừ điểm **Tuần Không** trực tiếp vào bảng điểm.
- **Knowledge:** Cập nhật tài liệu nội bộ về sự khác biệt giữa "Số lượng" (Quantity) và "Sức mạnh thực tế" (Quality/Seasonality) trong Bát Tự.

---

## [2026-03-23] - Advanced Bazi Features (Phase 05-07) ✅
- **Phase 07 COMPLETE:** Kiểm thử & Xác minh tổng thể (Testing & Verification).
- **Phase 06 COMPLETE:** Đồng bộ PromptBuilder & Rules.
- **Phase 05 COMPLETE:** Tính toán Lưu Niên (Annual Luck) cho 10 năm của đại vận hiện tại.
- **Verification:** Toàn bộ tính năng mới đã được xác minh bằng unit test `AdvancedFeaturesTest.kt` đối chiếu với lá số mẫu (Ngọc 1992).
- **Accuracy:** Khớp hoàn toàn các thông số Tuần Không, Thai Nguyên, Mệnh Cung, Đào Hoa, Thiên Y và Lưu Niên.
- **AI Integration:** JSON Prompt giờ đây chứa đầy đủ dữ liệu cao cấp để AI luận giải chuyên sâu từng năm.

---

## [2026-03-23] - Advanced Bazi Features (Phase 04)
- **Phase 04 COMPLETE:** Mở rộng Thần Sát (Expand Shen Sha).
- **Thần Sát mới:** Bổ sung Đào Hoa (Hàm Trì), Thiên Y, Hồng Loan, Thiên Hỉ.
- **Accuracy:** Đã verify khớp với yêu cầu chuyên gia: Đào Hoa & Thiên Y tại Giờ cho lá số mẫu (Ngọc 1992).
- **Technicals:** Cập nhật `BaZiConstants.kt` với maps tra cứu mới và nâng cấp `calculateShenSha` trong `BaZiLogic.kt`.

---

## [2026-03-23] - Advanced Bazi Features (Phase 03)
- **Phase 03 COMPLETE:** Thai Nguyên + Mệnh Cung (Trụ Phụ).
- **Thai Nguyên (Fetal Origin):** Triển khai logic tính trụ thụ thai dựa trên Trụ Tháng (Can tiến 1, Chi tiến 3).
- **Mệnh Cung (Life Palace):** Triển khai logic tính cung mệnh dựa trên công thức 26 - (MonthIdx + HourIdx) với mapping Dần=1. Tích hợp Ngũ Hổ Độn để tìm Thiên Can Mệnh Cung.
- **AI Integration:** Cập nhật PromptBuilder để truyền dữ liệu auxiliary_pillars (thai_origin, life_palace) vào JSON gửi Gemini.
- **Testing:** Đã bổ sung unit test `AuxiliaryPillarTest.kt` xác minh độ chính xác theo lá số mẫu (Ngọc 1992-10-23).
- **Environment:** Cập nhật `gradle.properties` sang Java 21 để đảm bảo tương thích môi trường build.

---

## [2026-03-23] - Advanced Bazi Features (Phase 02)
- **Phase 02 COMPLETE:** Tuần Không + Thập Thần Tàng Can.
- **Tuần Không (Void Branches):** Triển khai cách tính Niên Không (Năm) và Nhật Không (Ngày) dựa trên Lục Thập Hoa Giáp.
- **Thập Thần Tàng Can:** Gán Thập Thần cho toàn bộ các Tàng Can (Bản, Trung, Dư khí) giúp AI phân tích lực lượng ngũ hành ẩn dật.
- **AI Integration:** Cập nhật PromptBuilder để truyền dữ liệu xun_kong và ten_god của tàng can vào JSON interpreted by Gemini.
- **Testing:** Đã bổ sung unit test cho logic Tuần Không và cấu trúc JSON output.

---

## [2026-03-23] - Advanced Bazi Features (Phase 01)
- **Phase 01 COMPLETE:** Fix Interactions Bug (Phục Ngâm + Bán Hợp).
- **Tương tác Địa Chi:** Sửa lỗi Bán Tam Hợp nhận diện sai các chi trùng nhau (VD: Thân-Thân thành Bán Hợp Thủy).
- **Phục Ngâm:** Bổ sung logic tính toán tương tác Phục Ngâm khi 2 chi giống hệt nhau (trừ trường hợp Tự hình).
- **Testing:** Thêm test case xác minh Thân-Thân trả về chính xác Phục Ngâm, không phải Bán Hợp.

---

## [2026-03-23] - Bazi Logic Accuracy Overhaul (Phases 01-06) ✅
- **Phase 01-06 COMPLETE:** Đã hoàn thiện toàn bộ engine Bát Tự với độ chính xác tuyệt đối.
- **Tính toán Đại Vận (Luck Pillars):** Đã tích hợp logic tính mốc khởi vận chính xác theo Tiết khí (3 ngày = 1 năm, 12 phút = 1 ngày) kèm hiển thị "Năm-Tháng-Ngày" lẻ. (Fixed reset Calendar seconds/ms).
- **Tương tác Địa Chi:** Bổ sung nhận diện **Bán Tam Hợp** và **Củng Hợp** (động), ưu tiên Tam Hợp > Bán Hợp.
- **Thập Thần & Trường Sinh:** Fix lỗi gán Thập Thần trụ Ngày, đồng bộ 12 vòng Trường Sinh cho tất cả các trụ so với Nhật Chủ.
- **Thần Sát:** Phân tách rõ rệt Quý Nhân theo Năm và theo Ngày để tránh nhầm lẫn.
- **AI Integration:** Cập nhật `PromptBuilder.kt` với cấu trúc JSON đa tầng, cung cấp cho Gemini đầy đủ dữ liệu để luận giải chuyên sâu.
- **Testing:** Đã pass 100% unit tests (`BaZiLogicTest`, `LuckPillarTest`, `MigrationTest`).

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
