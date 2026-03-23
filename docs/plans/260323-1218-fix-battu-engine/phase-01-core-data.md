# Phase 01: Core Data Structure (Nạp Âm, Tàng Can, Thập Thần)
Status: ⬜ Pending
Dependencies: None

## Objective
Sửa đổi nền tảng dữ liệu cơ bản để giải quyết lỗi sai Nạp Âm và cơ chế chọn Main Tàng Can (Bản Khí).

## Requirements
### Functional
- [ ] Implement bảng tra cứu Lục Thập Hoa Giáp (60 Giáp Tý) tĩnh để ánh xạ Nạp Âm chuẩn (Ví dụ: Nhâm Thân -> Kiếm Phong Kim).
- [ ] Cập nhật cấu trúc `HiddenStem` có chia tỷ lệ rõ ràng (Bản Khí 60%, Trung Khí 30%, Dư Khí 10%).
- [ ] Sửa lại thuật toán tính Thập Thần cho Địa Chi: xác định đúng Thập Thần của Bản Khí (Main) thay vì random/map sai như lỗi của Thìn (Mậu/Ất/Quý).

## Files to Create/Modify
- `app/src/main/java/com/skul9x/battu/data/BaZiConstants.kt` - Tạo object chứa bảng tra Nạp Âm, Tàng Can.
- `app/src/main/java/com/skul9x/battu/data/BaZiData.kt` - Update data classes.

## Notes
Đây là phần cốt lõi, không sửa được phần này AI sẽ tiếp tục luận sai các trụ cơ bản.
---
Next Phase: phase-02-bazi-logic.md
