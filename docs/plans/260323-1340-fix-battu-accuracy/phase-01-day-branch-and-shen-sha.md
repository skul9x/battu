# Phase 01: Day Branch Ten God & Minor Shen Sha Fix
Status: 🟡 In Progress
Dependencies: None

## Objective
Sửa triệt để lỗi Thập Thần ở ô Trụ Ngày bị gán nhầm giá trị của Lệnh Tháng (Tháng). Rà soát và phân tách hiển thị Thiên Ất Quý Nhân cho cả Năm và Ngày.

## Requirements
### Functional
- [x] Object `TenGods` phải chứa property `dayBranchGod`.
- [x] Sửa mapping copy-paste trong phần `day_branch`.
- [x] Object `BaZiLogic` tính đúng Thập thần cho Can Ngày / Chi Ngày dựa vào Bảng Bản Khí tàng can.
- [x] Sửa bug báo sai `Thiên Ất Quý Nhân (Tỵ)` do Canh Kim (ngày) đang bị đánh chung với Nhâm (năm).

### Non-Functional
- [x] JSON prompt sinh ra phải chứa giá trị chính xác. Không gây fail bộ Test cũ.

## Implementation Steps
1. [x] **Update Models** - Thêm `dayBranchGod` vào `TenGods`.
2. [x] **Update `BaZiLogic.kt`** - Chắc chắn map đúng hàm `calculateTenGod`. Mảng Shen Sha thêm "Thiên Ất (Năm)" và "Thiên Ất (Ngày)".
3. [x] **Update `PromptBuilder.kt`** - Bind đúng biến `day_branch` thay vì `monthBranchGod`.

## Files Modified
- `...data/Models.kt` - Đã apply
- `...core/BaZiLogic.kt` - Đã add 1 phần, cần apply phần name Shen Sha.
- `...ai/PromptBuilder.kt` - Đã apply

## Test Criteria
- [ ] Chạy unit test Prompt builder.
- [ ] So sánh raw output của Nhật Chủ hiện thực/cũ.

---
Next Phase: `phase-02-life-stages.md`
