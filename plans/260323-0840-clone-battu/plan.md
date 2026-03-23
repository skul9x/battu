# Plan: Clone Bát Tự Engine + AI Luận Giải
Created: 2026-03-23T08:40:00+07:00
Status: 🟡 In Progress

## Overview
Clone thuật toán lập lá số Bát Tự (Tứ Trụ) từ app `TuViAndroid-BatTu` sang app Android mới `com.skul9x.battu`. App mới chuyên về:
1. Tính toán Tứ Trụ (Năm, Tháng, Ngày, Giờ)
2. Hiển thị lá số Bát Tự đẹp mắt
3. Tạo JSON prompt và gửi Gemini AI luận giải
4. Copy prompt / kết quả

## Tech Stack
- **Platform:** Android (Kotlin)
- **UI:** Jetpack Compose + Material 3
- **AI:** Google Gemini SDK (streaming)
- **Database:** Room (lịch sử)
- **Serialization:** Kotlinx Serialization

## Source Code Reference
| File | LOC | Mô tả |
|------|-----|-------|
| `BaZiLogic.kt` | 251 | Logic Bát Tự chính (TST, Tứ Trụ, Thập Thần) |
| `Constants.kt` | 512 | Thiên Can, Địa Chi, Ngũ Hành, Tàng Can... |
| `LunarConverter.kt` | 80 | Solar ↔ Lunar conversion |
| `LunarDateUtil.kt` | 169 | Julian Day, Lunar table lookups |
| `Models.kt` | 124 | Data classes (Pillar, BaZiData, TenGods) |
| `GeminiClient.kt` | 1469 | AI client (multi-key, streaming, JSON prompt) |
| `solar-term.json` | 278KB | 24 Tiết Khí data |

## Phases

| Phase | Name | Status | Tasks | Est. |
|-------|------|--------|-------|------|
| 01 | Project Setup & Dependencies | ⬜ Pending | 8 | 2-3h |
| 02 | Clone Core Bát Tự Logic | ⬜ Pending | 10 | 4-6h |
| 03 | UI - Nhập liệu & Hiển thị | ⬜ Pending | 12 | 8-12h |
| 04 | AI Integration (Gemini) | ⬜ Pending | 10 | 6-8h |
| 05 | History & Polish | ⬜ Pending | 8 | 4-6h |

**Tổng:** 48 tasks | Ước tính: 24-35 giờ (3-5 ngày)

## Quick Commands
- Start Phase 1: `/code phase-01`
- Check progress: `/next`
- Save context: `/save-brain`
