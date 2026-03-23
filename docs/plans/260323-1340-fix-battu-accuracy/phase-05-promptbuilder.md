# Phase 05: PromptBuilder & Final Verification
Status: ✅ Complete
Dependencies: phase-04

## Objective
Cập nhật `PromptBuilder` để truyền tải đầy đủ các thông tin mới (trạng thái 12 cung, tuổi khởi vận lẻ, tương tác bán hợp) vào JSON gửi cho AI.

## Implementation Steps
1. Cấu trúc lại `buildTenGodsJson` nếu cần.
2. Kiểm tra `element_balance` xem có cần update thêm điểm số cho Bán Hợp không.
3. Chạy toàn bộ Unit Test và so sánh với lá số gốc `hocvienlyso.org`.
4. Xuất file `prompt.txt` mới chuẩn xác.

## Test Criteria
- [x] Output JSON khớp hoàn toàn với các quy tắc mệnh lý đã sửa.
