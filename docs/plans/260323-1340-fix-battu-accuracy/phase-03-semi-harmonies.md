# Phase 03: Có/Không Bán Hợp (Tương Tác Địa Chi)
Status: ⬜ Pending

## Objective
Luận đoán của Thiên Mệnh thường thiếu chính xác nếu bỏ sót "Bán Tam Hợp" (Semi-Harmonies), chẳng hạn Thân - (Tý) - Thìn tạo cục Thủy ảo.

## Implementation Steps
- Add mảng BAN_TAM_HOP vào `BaZiConstants.kt`
- Kiểm tra các cặp 2 chi trong 4 trụ (so sánh Year-Month, Year-Day, Year-Hour...). Nếu khớp mảng thì gán "Bán Tam Hợp" vào Interactions.
