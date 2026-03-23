# Phase 04: Prompt & Output Update
Status: ⬜ Pending
Dependencies: Phase 03

## Objective
Cập nhật `PromptBuilder.kt` và cấu trúc `BaZiData` sang template JSON để đưa vào AI. Dữ liệu phải được truyền đầy đủ và sạch sẽ theo đúng format Tử Bình.

## Requirements
### Functional
- [ ] Cập nhật data class `BaZiData` chứa toàn bộ biến mới từ 3 phase trước.
- [ ] Viết lại `buildJsonPrompt()` trong `PromptBuilder`.
- [ ] Truyền chuỗi chuẩn của Nạp Âm.
- [ ] Thêm object mới: `seasonal_strength` (mùa), `twelve_states` (Trường sinh), `shen_sha` (Thần Sát).
- [ ] Cấu trúc lại trường `hidden_stems` thành Array Object có trường `%` (trọng số).
- [ ] Thêm object `interactions` (Hợp/Xung/Hình) và mảng `luck_pillars` (Đại Vận).

## Files to Create/Modify
- `app/src/main/java/com/skul9x/battu/ai/PromptBuilder.kt`

## Notes
Chỉ map những field đã được parse cẩn thận ở các phase trước để tránh JSON phình to bị lỗi token hoặc gây nhiễu cho AI. Prompt càng cấu trúc mạch lạc, AI phán càng chuẩn.
