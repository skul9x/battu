# Phase 04: Calculate Luck Pillars Age Accuracy
Status: ⬜ Pending

## Objective
Hiện tại giao vận đang fix cứng 1-10. Cần tạo ra được một phép chia để ra được năm sinh thứ mấy thì bước vào Đại Vận thứ 1.

## Rules
- Thuận vận (Nam Dương - Nữ Âm): Tính số ngày từ lúc sinh tới Lập tiết kế tiếp.
- Nghịch vận (Nam Âm - Nữ Dương): Tính số ngày lùi quá khứ tới Lập tiết gần nhất.
- Lấy (Số_ngày / 3) để ra số tuổi khởi vận. 

## Implementation
- Chỉnh lại Logic Age Start trong `calculateLuckPillars` để return chính xác số tuổi. Thay vì (i * 10 + 1), dùng `startAge + (i * 10)`.

---
Next: Phase 05 - Finish JSON Dump Update & End.
