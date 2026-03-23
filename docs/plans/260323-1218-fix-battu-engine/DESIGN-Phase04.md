# 🎨 DESIGN: Bát Tự AI - Phase 04 (Prompt & Output Update)

Ngày tạo: 2026-03-23
Dựa trên: docs/plans/260323-1218-fix-battu-engine/phase-04-prompt-update.md

---

## 1. Cách Lưu Thông Tin (JSON Payload cho AI)

Để AI có thể đọc hiểu chính xác lá số Bát Tự, chúng ta tạo ra một bản tóm tắt gửi cho AI dưới dạng JSON. Dưới đây là cấu trúc dữ liệu mới sẽ được build:

📦 SƠ ĐỒ LƯU TRỮ (JSON TRUYỀN CHO AI):
┌─────────────────────────────────────────────────────────────┐
│ 🧠 BAZI_PROMPT (Dữ liệu gửi cho Gemini)                     │
│ ├── Thông tin cá nhân (Tên, Giới tính)                      │
│ ├── Tứ Trụ (Năm, Tháng, Ngày, Giờ)                          │
│ │   ├── Thiên Can, Địa Chi + Ngũ Hành tương ứng             │
│ │   ├── Nạp Âm (VD: Hải Trung Kim)                          │
│ │   ├── Vòng Trường Sinh (VD: Mộc Dục, Đế Vượng)            │
│ │   └── Tàng Can (Gồm %, loại khí)                          │
│ ├── Ngũ Hành (Thống kê số lượng Kim/Mộc/Thủy/Hỏa/Thổ)       │
│ │   ├── Mùa sinh (Xuân/Hạ/Thu/Đông)                         │
│ │   └── Trạng thái Nhật Chủ (Vượng/Tướng/Hưu/Tù/Tử)         │
│ ├── Thần Sát (Các sao tốt/xấu: Thiên Ất, Đào Hoa...)        │
│ ├── Tương Tác Hình/Xung/Khắc/Hại (VD: Lục Xung, Tam Hợp)    │
│ └── Đại Vận (Danh sách các vận 10 năm)                      │
└─────────────────────────────────────────────────────────────┘

💡 Giống như việc tóm tắt hồ sơ khám bệnh trước khi đưa bác sĩ chuyên khoa. Hồ sơ càng rõ ràng, chi tiết, bác sĩ khám càng nhanh và chuẩn!

## 2. Thông Tin Chi Tiết Các Hàm Cần Sửa (PromptBuilder.kt)

Thay vì gọi dữ liệu đơn giản như trước, ta sẽ viết thêm logic để lấy mọi trường dữ liệu từ core engine ra JSON.

### 2.1 Cập nhật `buildPillarJson()`
- Bổ sung `life_stage` vào object trụ.
- Sửa lại trường `hidden_stems` thành Array các Object thay vì Array String cứng nhắc.

```json
"hidden_stems": [
  { "stem": "Giáp", "percentage": 60, "type": "BAN_KHI" }
]
```

### 2.2 Cập nhật `buildChartData()`
- Trong block `element_balance`: Bổ sung thêm `season` và `day_master_strength`.
- Khởi tạo khối xuất `shen_sha` (Thần Sát), `interactions` (Tương Tác), và `luck_pillars` (Đại Vận).

### 2.3 Viết thêm các Private Methods Mapper Json
- `buildShenShaJson(list)`
- `buildInteractionsJson(list)`
- `buildLuckPillarsJson(list)`

## 3. Luồng Hoạt Động (Hành trình Build Prompt)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📍 HÀNH TRÌNH TẠO PROMPT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1️⃣ App tính toán xong dữ liệu thô `BaZiData`.
2️⃣ Người dùng ấn nút "Gửi AI". App gọi hàm `PromptBuilder.buildJsonPrompt(baZiData)`.
3️⃣ `PromptBuilder` dịch từ Data Class Kotlin → JSON string một cách gọn gàng, rõ nghĩa nhất.
4️⃣ JSON Data được nhúng vào template Prompt chuẩn gồm rules, system instruction.
5️⃣ Gửi chuỗi Prompt siêu cấp lên Gemini.
6️⃣ Nhận về Markdown phân tích chuẩn Tử Bình không chém gió!

Anh thấy luồng này tự nhiên và hợp lý chưa?

## 4. Checklist Kiểm Tra (Làm sao biết code chuẩn?)

📋 Tính năng: Tối ưu hoá Prompt JSON cho AI
SPECS Reference: Phase 04

✅ **Cơ bản:**
  - [ ] `PromptBuilder` không báo lỗi compile (do sai thuộc tính mô hình).
  - [ ] Update `buildChartData` để lấy đủ `season` và `dayMasterStrength`.
  - [ ] Viết hàm convert list `ShenSha` ra mảng JSON.
  - [ ] Viết hàm convert list `Interaction` ra mảng JSON.
  - [ ] Viết hàm convert list `LuckPillar` ra mảng JSON.

✅ **Nâng cao:**
  - [ ] Chỉnh sửa `buildPillarJson` để xuất `hidden_stems` dưới dạng object thay vì text string nối tự do, AI sẽ dễ hiểu con số phần trăm hơn.
  - [ ] Kiểm tra JSON đầu ra xem có phình quá to không? Nếu có thì bỏ bớt phần "type/description" dài dòng.

## 5. Test Cases Thiết Kế (Chuẩn bị Test)

📝 TEST CASES: Output JSON cho AI

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TC-01: Tính hợp lệ của object Tàng Can
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Given: Một Lá số có Tàng Can ở Địa Chi
When:  Gọi hàm tạo JSON cho Trụ
Then:  ✓ Node `hidden_stems` phải chứa mảng object chứa: `stem`, `percentage`, `type`.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TC-02: Thần sát & Tương tác
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Given: Lá số có dữ liệu mảng ShenSha và Interactions
When:  Gọi hàm `buildJsonPrompt`
Then:  ✓ Chuỗi kết quả có chứa block `"shen_sha": [...]`.
       ✓ Chuỗi kết quả có chứa block `"interactions": [...]`.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TC-03: Đại vận
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Given: Lá số có mảng LuckPillars
When:  Gọi hàm `buildJsonPrompt`
Then:  ✓ Mảng `"luck_pillars"` hiển thị rành mạch `start_age`, `end_age`, `stem`, `branch`.

---
*Tạo bởi AWF 2.1 - Design Phase*
