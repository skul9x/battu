━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📋 HANDOVER DOCUMENT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

📍 Đang làm: Advanced Bazi Features
🔢 Đến bước: Phase 03: Thai Nguyên + Mệnh Cung

✅ ĐÃ XONG:
   - Phase 01: Fix Interactions (Phục Ngâm + Bán Hợp) ✓
   - Phase 02: Tuần Không + Thập Thần Tàng Can ✓
     - getXunKong in BaZiConstants.kt
     - tenGod in HiddenStem mapping
     - AI Prompt JSON exports

⏳ CÒN LẠI:
   - Phase 03: Thai Nguyên + Mệnh Cung
   - Phase 04: Expand Shen Sha (Đào Hoa, Thiên Y, Hồng Loan, Thiên Hỉ)
   - Phase 05: Annual Luck (Lưu Niên)
   - Phase 06: PromptBuilder Sync
   - Phase 07: Testing & Verification

🔧 QUYẾT ĐỊNH QUAN TRỌNG:
   - Thập Thần Tàng Can được tính cho cả 3 khí (Bản, Trung, Dư) để AI thấy chi tiết.
   - Tuần Không tính cho cả 年 (Năm) và 日 (Ngày).
   - Luck Pillar (Đại Vận) giữ nguyên độ chính xác Năm-Tháng-Ngày (không làm tròn).

⚠️ LƯU Ý CHO SESSION SAU:
   - Logic Thai Nguyên: Tháng sinh lùi 3 chi, Can sinh tiến 1 can.
   - Logic Mệnh Cung: Dùng giờ sinh và tháng sinh tra bảng hoặc công thức 26 - (Giờ + Tháng).

📁 FILES QUAN TRỌNG:
   - app/src/main/java/com/skul9x/battu/data/BaZiConstants.kt
   - app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt
   - .brain/session.json (progress)
   - .brain/session_log.txt (chi tiết)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📍 Đã lưu! Để tiếp tục: Gõ /recap
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
