# Plan: Bazi Logic Accuracy Fix
Created: 2026-03-23 13:40
Status: 🟡 In Progress

## Overview
Dựa trên báo cáo lỗi logic chuyên sâu, chúng ta cần khắc phục 5 điểm sai phạm nghiêm trọng trong việc tính toán và hiển thị dữ liệu Bát Tự. Mục tiêu là đảm bảo engine trả về kết quả chuẩn xác 100% so với lý thuyết Bát Tự Tử Bình truyền thống, đặc biệt là Thập Thần, Thần Sát, vòng Trường Sinh và các tương tác Bán Hợp.

## Tech Stack
- Backend/Engine: Kotlin (Core Logic)
- App Architecture: Jetpack Compose, ViewModels
- AI Integration: Gemini API with JSON prompting

## Phases

| Phase | Name | Status | Progress |
|-------|------|--------|----------|
| 01 | Day Branch Ten God Fix | ⬜ Pending | 0% |
| 02 | Accurate Shen Sha (Thiên Ất & More) | ⬜ Pending | 0% |
| 03 | 12 Life Stages Refactor | ⬜ Pending | 0% |
| 04 | Semi-Harmonies (Bán Hợp) Integration | ⬜ Pending | 0% |
| 05 | Luck Pillars Age Calculation | ⬜ Pending | 0% |
| 06 | PromptBuilder Update & Testing | ⬜ Pending | 0% |

## Detailed Breakdown

### Phase 01: Day Branch Ten God Fix
- **Vấn đề:** Trụ Ngày đang bị gán sai Thập Thần của chi Tháng.
- **Giải pháp:** Sửa trong `PromptBuilder.kt` để lấy đúng `dayBranchGod` từ object `TenGods`. (Đã làm nháp nhưng cần rà soát toàn bộ model TenGods để đảm bảo tính nhất quán).

### Phase 02: Accurate Shen Sha (Thiên Ất & More)
- **Vấn đề:** Nhận diện sai Thiên Ất Quý Nhân cho từng trụ, dẫn đến trùng lặp hoặc sai lệch (như Tỵ cho Canh Kim).
- **Giải pháp:** 
    1. Tách biệt bộ tra cứu Thiên Ất của **Năm** và của **Ngày**.
    2. Cập nhật `BaZiLogic.kt` để khi add `ShenSha`, phân rõ "Thiên Ất (Năm)" và "Thiên Ất (Ngày)" để AI và User không bị rối.

### Phase 03: 12 Life Stages Refactor
- **Vấn đề:** Gán trạng thái Vượng/Tuyệt của Nhật Chủ theo mùa là chưa chuẩn xác, phải dùng 12 Vòng Trường Sinh.
- **Giải pháp:** 
    1. Sửa hàm `getDayMasterStrength` hoặc thêm một biến mới để biểu thị Trạng thái 12 Cung của Nhật Chủ tại chi Tháng (Lệnh tháng).
    2. Đảm bảo Canh sinh tháng Dần trả về "Tuyệt".

### Phase 04: Semi-Harmonies (Bán Hợp) Integration
- **Vấn đề:** Đang thiếu các tương tác Bán Tam Hợp (như Thân-Thìn tạo Thủy cục).
- **Giải pháp:** Thêm danh sách `BAN_TAM_HOP` vào `BaZiConstants.kt` và update logic check trong `calculateInteractions`.

### Phase 05: Luck Pillars Age Calculation
- **Vấn đề:** Tuổi khởi vận đang bị fix cứng 1-10, 11-20...
- **Giải pháp:** 
    1. Viết lại logic trong `calculateLuckPillars` để tính đúng số ngày từ ngày sinh đến Lập Xuân/tiết khí gần nhất, chia 3 để ra số tuổi khởi vận. 
    2. Update field `startAge` và `endAge` theo vòng lặp khởi vận chuẩn.

### Phase 06: PromptBuilder Update & Testing
- **Vấn đề:** Sau khi core logic thay đổi, Prompt JSON cần được cấu trúc lại để chứa đủ thông tin mới.
- **Giải pháp:** 
    1. Update `PromptBuilder.kt` để phản ánh đúng cấu trúc.
    2. Review JSON dump và chạy `MigrationTest` để đảm bảo code không break history.

## Quick Commands
  - Start Phase 1: `/code phase-01-day-branch-ten-god.md`
  - Check progress: `/next`
  - Save context: `/save-brain`
