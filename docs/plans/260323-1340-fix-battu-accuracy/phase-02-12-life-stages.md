# Phase 02: 12 Life Stages Refactor (Trường Sinh 12 Cung)
Status: ⬜ Pending
Dependencies: phase-01

## Objective
Thay vì dùng trạng thái ngũ hành theo mùa chung chung (Vượng/Tướng/Hưu/Tù/Tử), luận mệnh Bát Tự thực chiến cần sử dụng "12 Vòng Trường Sinh" để xác định độ vượng tử của Nhật Chủ khi so với chi Tháng (lệnh tháng) hoặc tại chính trụ của nó. Mục tiêu là sửa lỗi Nhật Chủ Canh Kim sinh tháng Dần báo "Tử", thực tế là "Tuyệt".

## Requirements
### Functional
- [ ] Cập nhật lại cách hiển thị hoặc thêm vào logic tính Vượng Tuyệt của thiên can theo 12 cung.
- [ ] Tính và đánh giá đúng 12 Vòng Trường Sinh cho Nhật Chủ (Day Master) đối chiếu với Địa chi của các trụ.
- [ ] Thêm logic tính cung Nhật Chủ so với Địa Chi Tháng (Lệnh tháng - quyết định 50% độ vượng nhược).

### Non-Functional
- [ ] Tương thích với JSON schema hiện tại hoặc mở rộng sao cho dễ hiểu nhất.

## Implementation Steps
1. [ ] Cấu trúc lại bảng tính vòng trường sinh để có thể truy xuất "Day Master Strength" bằng hàm `getLifeStage(dayMaster, monthBranch)`.
2. [ ] Update `BaZiLogic.kt` thay từ "Tử" thành "Tuyệt". 
3. [ ] Cập nhật `PromptBuilder.kt` ánh xạ lại thông số nếu có sự thay đổi key name.

## Test Criteria
- [ ] So sánh `getLifeStage("Canh", "Dần")` trả về `"Tuyệt"`.
- [ ] So sánh `getLifeStage("Canh", "Thìn")` trả về `"Dưỡng"`.

---
Next Phase: `phase-03-semi-harmonies.md`
