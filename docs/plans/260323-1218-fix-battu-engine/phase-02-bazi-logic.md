# Phase 02: Bazi Basic Logic (Mùa, Trường Sinh, Trọng Số Ngũ Hành)
Status: ⬜ Pending
Dependencies: Phase 01

## Objective
Tính toán vòng Trường Sinh, Mùa sinh (Lệnh tháng) và chuẩn hóa lại điểm số cân bằng Ngũ Hành có tỷ trọng.

## Requirements
### Functional
- [ ] Thuật toán xác định Mùa dựa vào Lệnh Tháng (Địa Chi Tháng) và tác động lên đánh giá Nhật Chủ (Ví dụ: Mộc vượng mùa Xuân, Hỏa vượng mùa Hạ...).
- [ ] Xây dựng bảng tra Vòng Thập Nhị Trường Sinh (Trường Sinh, Mộc Dục, Quan Đới... Tuyệt, Thai, Dưỡng) cho Nhật Chủ áp dụng lên 4 Địa Chi.
- [ ] Xây dựng lại logic `element_balance`: Tính điểm ngũ hành dựa trên Thấu Can và Tàng Can có phân bổ tỷ trọng (Bản Khí cao hơn Dư Khí) thay vì gán số ngẫu nhiên.

## Files to Create/Modify
- `app/src/main/java/com/skul9x/battu/engine/BaZiCalculator.kt` - Có thể bắt đầu cân nhắc tách Engine rời khỏi ViewModel để dễ build.

## Notes
Hãy viết hàm tra cứu rõ ràng logic. Việc phân bổ điểm số cần làm khoa học, không chế số.

---
Next Phase: phase-03-advanced-bazi.md
