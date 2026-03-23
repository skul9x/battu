# Phase 03: Semi-Harmonies (Bán Tam Hợp)
Status: ✅ Complete
Dependencies: phase-02

## Objective
Luận đoán Bát Tự thường thiếu chính xác nếu bỏ sót "Bán Tam Hợp" (Semi-Harmonies), ví dụ cặp Thân - Thìn (thiếu Tý) vẫn tạo ra một luồng khí Thủy mạnh.

## Requirements
### Functional
- [x] Bổ sung bảng tra cứu `BAN_TAM_HOP` vào `BaZiConstants.kt`.
- [x] Cập nhật logic `calculateInteractions` trong `BaZiLogic.kt` để phát hiện các cặp Bán Hợp.
- [x] Phân biệt "Bán Hợp" (2 chi đầu-cuối hoặc đầu-giữa) và "Tam Hợp" (đủ 3 chi).

## Implementation Steps
1. Thêm data structure cho Bán Hợp.
2. Viết hàm check cặp đôi chi trong danh sách 4 trụ.
3. Thêm vào mảng trả về của `interactions`.

## Test Criteria
- [x] Lá số Nhâm Thân / Canh Thìn phải báo "Bán Hợp Thủy (Thân-Thìn)".
