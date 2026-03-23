━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📋 HANDOVER DOCUMENT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

📍 Đang làm: Bazi Logic Accuracy Fix (Precision Overhaul)
🔢 Đến bước: Phase 04: Semi-Harmonies Done -> Phase 05: Luck Pillars Age

✅ ĐÃ XONG:
   - Phase 01: Sửa lỗi Thập Thần trụ Ngày bị gán sai Lệnh Tháng. ✓
   - Phase 02: Tách biệt Thiên Ất Quý Nhân (Năm vs Ngày). ✓
   - Phase 03: Vòng Trường Sinh 12 Cung (Tinh chỉnh Canh/Dần -> Tuyệt). ✓
   - Phase 04: Bán Tam Hợp & Củng Hợp (Refactor sang dynamic check). ✓

⏳ CÒN LẠI:
   - Phase 05: Tuổi khởi vận (startAge) chính xác dựa trên tọa độ/tiết khí (3 ngày = 1 năm).
   - Phase 06: Đồng bộ hóa toàn bộ JSON output trong PromptBuilder.kt.

🔧 QUYẾT ĐỊNH QUAN TRỌNG:
   - Logic Bán Hợp/Củng Hợp được refactor thành dạng Duyệt Duy nhất theo element-outcome (BaZiConstants.TAM_HOP), giúp code gọn và dễ mở rộng (như Thổ cục).
   - Tiếp tục giữ tính tương thích ngược cho dữ liệu JSON lịch sử qua `PillarCard` và `MigrationTest`.

⚠️ LƯU Ý CHO SESSION SAU:
   - Đã pass Test suite (BaZiLogicTest, BaZiConstantsTest).
   - File `BaZiLogic.kt` đang ổn định cho phần Interactions.
   - Cần kiểm tra lại logic tính khoảng cách ngày tới Tiết khí (hiện đang fix cứng Calendar reset seconds/ms).

📁 FILES QUAN TRỌNG:
   - `docs/plans/260323-1340-fix-battu-accuracy/plan.md` (Master Plan)
   - `app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt`
   - `.brain/session.json`

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📍 Đã lưu! Để tiếp tục: Gõ /recap
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
