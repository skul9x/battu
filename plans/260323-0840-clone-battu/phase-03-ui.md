# Phase 03: UI - Nhập liệu & Hiển thị Lá Số
Status: ✅ Completed (2026-03-23)
Dependencies: Phase 02

## Objective
Xây dựng toàn bộ giao diện người dùng bằng Jetpack Compose: Màn hình nhập liệu, hiển thị Tứ Trụ, và kết quả AI.

## Requirements

### Functional
- [ ] Form nhập: Tên, Giới tính, Ngày/Tháng/Năm sinh, Giờ sinh
- [ ] Toggle Dương lịch ↔ Âm lịch
- [ ] Hiển thị 4 cột Tứ Trụ (Giờ - Ngày - Tháng - Năm)
- [ ] Màu sắc theo Ngũ Hành
- [ ] Hiển thị Tàng Can trong mỗi trụ
- [ ] Hiển thị Thập Thần
- [ ] Hiển thị Ngũ Hành balance (biểu đồ)
- [ ] Navigation giữa các màn hình

### Non-Functional
- [ ] Material 3 Design
- [ ] Responsive cho nhiều kích thước màn hình
- [ ] Dark/Light theme support
- [ ] Animation mượt mà

## Implementation Steps

### 1. Tạo Theme
- [ ] `com.skul9x.battu.ui.theme.Color.kt` — Bảng màu Ngũ Hành:
  ```
  Mộc → Xanh lá (#4CAF50)
  Hỏa → Đỏ (#F44336)
  Thổ → Vàng nâu (#FF9800)
  Kim → Trắng xám (#9E9E9E)
  Thủy → Xanh dương (#2196F3)
  ```
- [ ] `Theme.kt` — Material 3 theme (Dark + Light)
- [ ] `Type.kt` — Typography chuẩn

### 2. Tạo Navigation
- [ ] Định nghĩa `Screen` sealed class:
  - `InputScreen` — Màn hình nhập liệu
  - `ChartScreen` — Hiển thị Tứ Trụ
  - `ResultScreen` — Kết quả AI
  - `HistoryScreen` — Lịch sử (Phase 05)
- [ ] Setup `NavHost` trong `BatTuApp()`

### 3. Input Screen (Màn hình nhập liệu)
- [ ] Card layout với Material 3
- [ ] **TextField:** Họ tên
- [ ] **SegmentedButton:** Giới tính (Nam / Nữ)
- [ ] **Switch:** Dương lịch ↔ Âm lịch
- [ ] **DatePicker:** Chọn ngày sinh (Material 3 DatePicker)
- [ ] **Dropdown/Slider:** Chọn giờ sinh (12 canh giờ: Tý → Hợi)
  - Hiển thị dạng: "Giờ Tý (23:00 - 00:59)"
- [ ] **Optional TextField:** Kinh độ (default: 105.8 - Hà Nội)
- [ ] **Checkbox:** "Đổi ngày lúc 23:00" (default: checked)
- [ ] **Button:** "Lập Lá Số Bát Tự" → Navigate to ChartScreen
- [ ] Validation: Năm 1900-2049, ngày/tháng hợp lệ

### 4. Chart Screen (Hiển thị Tứ Trụ)
- [ ] **Header:** Thông tin người dùng (tên, ngày sinh, TST info)
- [ ] **Tứ Trụ Grid:** 4 cột, mỗi cột hiển thị:
  ```
  ┌─────────┐
  │ Thập Thần│  ← Ten God
  ├─────────┤
  │ Thiên Can│  ← Stem (color by element)
  ├─────────┤
  │ Địa Chi  │  ← Branch (color by element)
  ├─────────┤
  │ Tàng Can │  ← Hidden Stems
  └─────────┘
     Năm       ← Label
  ```
- [ ] Color-code theo Ngũ Hành (background tint)
- [ ] Âm/Dương indicators (♂/♀ hoặc ⚊/⚋)
- [ ] **Ngũ Hành Balance Bar:** Horizontal bar chart 5 hành
- [ ] **Tiết Khí Info:** Hiện tại + tiếp theo
- [ ] **Nạp Âm:** Hiển thị nạp âm năm sinh
- [ ] **Action Buttons:**
  - "📋 Copy JSON Prompt" → Copy to clipboard
  - "🤖 Gửi AI Luận Giải" → Navigate to ResultScreen
  - "💾 Lưu Lá Số" → Save to history

### 5. Loading & Error Components
- [ ] `LoadingIndicator.kt` — CircularProgressIndicator + animated text
- [ ] `ErrorDialog.kt` — AlertDialog cho lỗi API/network
- [ ] Shimmer effect khi đang load AI response

### 6. Reusable Components
- [ ] `PillarCard.kt` — 1 trụ (Can + Chi + Tàng Can)
- [ ] `ElementBadge.kt` — Badge hiển thị Ngũ Hành (icon + color)
- [ ] `ElementBalanceChart.kt` — Bar chart Ngũ Hành
- [ ] `SectionCard.kt` — Card container cho mỗi section

### 7. ViewModel
- [ ] `BatTuViewModel.kt`:
  - State: `UserInput`, `BaZiData?`, `isLoading`, `error`
  - Functions: `calculateBaZi()`, `generateJsonPrompt()`, `sendToAI()`
  - Load `solar-term.json` từ assets
  - Copy to clipboard function

## Files to Create
| File | Package | Mô tả |
|------|---------|-------|
| `Color.kt` | `ui.theme` | Ngũ Hành colors |
| `Theme.kt` | `ui.theme` | Material 3 theme |
| `Type.kt` | `ui.theme` | Typography |
| `Screen.kt` | `ui` | Navigation routes |
| `InputScreen.kt` | `ui.screens` | Form nhập liệu |
| `ChartScreen.kt` | `ui.screens` | Hiển thị Tứ Trụ |
| `PillarCard.kt` | `ui.components` | Card cho 1 trụ |
| `ElementBadge.kt` | `ui.components` | Badge ngũ hành |
| `ElementBalanceChart.kt` | `ui.components` | Biểu đồ ngũ hành |
| `LoadingIndicator.kt` | `ui.components` | Loading animation |
| `ErrorDialog.kt` | `ui.components` | Error dialog |
| `BatTuViewModel.kt` | `ui.viewmodel` | State management |

## Test Criteria
- [ ] Input screen hiển thị đầy đủ fields
- [ ] Date picker cho phép chọn 1900-2049
- [ ] Giờ sinh dropdown hiển thị 12 canh giờ
- [ ] Chart screen hiển thị 4 cột với đúng màu
- [ ] Tàng Can hiển thị đúng cho mỗi Địa Chi
- [ ] Navigation hoạt động smooth
- [ ] Dark mode hiển thị tốt

## Design Reference
Tham khảo thiết kế Tứ Trụ truyền thống:
```
   Giờ      Ngày     Tháng     Năm
┌────────┬────────┬────────┬────────┐
│ Thực   │ (Nhật  │ Chính  │ Thất   │ ← Thập Thần
│ Thần   │  Chủ)  │ Ấn    │ Sát   │
├────────┼────────┼────────┼────────┤
│  Canh  │  Mậu   │  Bính  │  Giáp  │ ← Thiên Can
│ (Kim)  │ (Thổ)  │ (Hỏa)  │ (Mộc)  │
├────────┼────────┼────────┼────────┤
│  Thân  │  Thìn  │  Dần   │  Tý   │ ← Địa Chi
│ (Kim)  │ (Thổ)  │ (Mộc)  │ (Thủy) │
├────────┼────────┼────────┼────────┤
│Canh    │Mậu     │Giáp    │Quý    │ ← Tàng Can
│Nhâm    │Ất      │Bính    │       │
│Mậu     │Quý     │Mậu     │       │
└────────┴────────┴────────┴────────┘
```

---
Next Phase: [phase-04-ai.md](./phase-04-ai.md)
