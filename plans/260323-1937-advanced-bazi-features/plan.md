# Plan: Advanced Bazi Features (Expert Feedback Overhaul)
Created: 2026-03-23T19:37:09+07:00
Status: 🟡 In Progress

## Overview
Triển khai các tính năng nâng cao cho engine Bát Tự dựa trên phản hồi từ 2 chuyên gia:
- Fix bug logic (Phục Ngâm, Bán Hợp)
- Thêm Tuần Không, Thai Nguyên, Mệnh Cung
- Mở rộng Thần Sát (Đào Hoa, Thiên Y, Hồng Loan, Thiên Hỉ)
- Thêm Lưu Niên (10 năm trong Đại Vận hiện tại)
- Gắn Thập Thần cho từng Tàng Can

## Tech Stack
- Language: Kotlin 2.0.20
- Framework: Jetpack Compose
- AI: Google Gemini SDK
- Testing: JUnit 4

## Reference
- Ảnh tham chiếu: `Ngoc - hocvienlyso.org.png`
- Expert feedback: `improves.txt`, `improves2.txt`
- App output: `Ngoc.txt`

## Decisions
- ✅ Giữ nguyên Đại Vận chính xác (không làm tròn)
- ✅ Lưu Niên: Chỉ 10 năm trong Đại Vận hiện tại (tiết kiệm token Gemini)

## Phases

| Phase | Name | Status | Progress | Tasks |
|-------|------|--------|----------|-------|
| 01 | Fix Interactions Bug | ✅ Complete | 100% | 4 |
| 02 | Tuần Không + Thập Thần Tàng Can | ✅ Complete | 100% | 6 |
| 03 | Thai Nguyên + Mệnh Cung | ⬜ Pending | 0% | 5 |
| 04 | Mở rộng Thần Sát | ⬜ Pending | 0% | 5 |
| 05 | Lưu Niên (Annual Luck) | ⬜ Pending | 0% | 4 |
| 06 | PromptBuilder Sync | ⬜ Pending | 0% | 4 |
| 07 | Testing & Verification | ⬜ Pending | 0% | 8 |

**Tổng:** 36 tasks | Ước tính: 3-4 sessions

## Quick Commands
- Start Phase 1: `/code phase-01`
- Check progress: `/next`
- Save context: `/save-brain`

## Files Affected
| File | Phases |
|------|--------|
| `data/Models.kt` | 01, 02, 03, 05 |
| `data/BaZiConstants.kt` | 02, 04 |
| `core/BaZiLogic.kt` | 01, 02, 03, 04, 05 |
| `ai/PromptBuilder.kt` | 02, 03, 05, 06 |
| `test/.../AdvancedFeaturesTest.kt` | 07 (NEW) |
