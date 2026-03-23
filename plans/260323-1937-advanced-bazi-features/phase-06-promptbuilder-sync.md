# Phase 06: PromptBuilder & Rules Sync
Status: ⬜ Pending
Dependencies: Phase 01, 02, 03, 04, 05

## Objective
Đồng bộ toàn bộ thay đổi từ Phase 01-05 vào PromptBuilder JSON output:
- Đảm bảo ALL sections mới đều được serialize
- Cập nhật `absolute_rules` để AI biết xét tính năng mới
- Cập nhật `analysis_pipeline` thêm tầng phân tích mới

## Implementation Steps

1. [ ] **PromptBuilder.kt** — Cập nhật `buildAbsoluteRules()`
   ```kotlin
   // Thêm vào must_do:
   "Xét Tuần Không (Không Vong): Chi bị Tuần Không sẽ giảm lực lượng",
   "Đánh giá Thai Nguyên và Mệnh Cung để bổ trợ luận đoán",
   "Kiểm tra Phục Ngâm (trụ trùng) ảnh hưởng tới ổn định lá số",
   "Sử dụng Lưu Niên của Đại Vận hiện tại để dự báo cụ thể từng năm"
   ```

2. [ ] **PromptBuilder.kt** — Cập nhật `buildAnalysisPipeline()`
   ```kotlin
   // Thêm tầng mới:
   "Tầng Tuần Không: Xét chi bị Không Vong → giảm điểm lực lượng Ngũ Hành tương ứng",
   "Tầng Trụ Phụ: Thai Nguyên (gốc rễ) + Mệnh Cung (hậu vận) bổ trợ luận đoán"
   ```

3. [ ] **PromptBuilder.kt** — Verify toàn bộ JSON structure
   - `chart_data.xun_kong` ← Phase 02
   - `chart_data.auxiliary_pillars` ← Phase 03
   - `chart_data.shen_sha` (expanded) ← Phase 04
   - `chart_data.interactions` (with Phục Ngâm) ← Phase 01
   - `chart_data.luck_pillars[].annual_pillars` ← Phase 05
   - `chart_data.pillars.*.hidden_stems[].ten_god` ← Phase 02

4. [ ] **Generate và review sample JSON** cho lá số Ngọc → so sánh với `improves2.txt`

## Files to Modify
- `app/src/main/java/com/skul9x/battu/ai/PromptBuilder.kt`

## Test Criteria
- [ ] JSON output valid (parsable)
- [ ] Tất cả sections mới đều có mặt
- [ ] `absolute_rules` bao gồm Tuần Không, Phục Ngâm, Thai Nguyên
- [ ] Sample output cho Ngọc match expectations

---
Next Phase: [phase-07-testing.md](./phase-07-testing.md)
