# Phase 04: AI Integration (Gemini)
Status: ⬜ Pending
Dependencies: Phase 02, Phase 03

## Objective
Tích hợp Google Gemini AI để luận giải lá số Bát Tự. Tham khảo `GeminiClient.kt` từ source cũ (1469 LOC) và đơn giản hóa cho Bát Tự.

## Source Reference
**File:** `/home/skul9x/Desktop/Test_code/TuViAndroid-BatTu/app/src/main/java/com/example/tviai/core/GeminiClient.kt`

### Các tính năng cần clone từ source:
| Feature | Source (line) | Clone? | Adapt? |
|---------|--------------|--------|--------|
| Multi API key support | L15-19 | ✅ | Giữ nguyên |
| Model priority fallback | L26-32 | ✅ | Giữ nguyên |
| testConnection() | L37-52 | ✅ | Giản hóa |
| isQuotaError() | L57-64 | ✅ | Giữ nguyên |
| getPromptForCopy() | L86-88 | ✅ | Adapt cho Bát Tự |
| generateReadingStream() | L101-173 | ✅ | Giữ nguyên logic |
| constructPrompt() | L175-225 | ✅ | **Viết lại hoàn toàn** cho Bát Tự |
| JSON prompt helpers | L862-1468 | ❌ | Viết mới cho Bát Tự |

### Các tính năng KHÔNG clone:
- `constructPromptLegacy()` — Legacy text prompt
- Tử Vi specific builders (buildChartDataJson, buildPalacesJsonArray, etc.)
- Star detection (detectBoSao)
- 12-palace analysis methods

## Requirements

### Functional
- [ ] Gọi Gemini API với streaming response
- [ ] Multi API key với auto-fallback
- [ ] Model priority list (gemini-3-flash → gemini-2.5-flash → ...)
- [ ] JSON prompt builder cho Bát Tự
- [ ] Copy JSON prompt to clipboard
- [ ] Copy AI result to clipboard
- [ ] System Instruction: Chuyên gia Bát Tự Tử Bình
- [ ] Error handling (quota, network, invalid key)

### Non-Functional
- [ ] Streaming text (hiệu ứng chữ chạy từng từ)
- [ ] Response time < 30 giây cho full analysis
- [ ] API key bảo mật (không hardcode)

## Implementation Steps

### 1. Tạo GeminiClient
- [ ] `com.skul9x.battu.ai.GeminiClient.kt`
- [ ] Clone cấu trúc từ source:
  ```kotlin
  class GeminiClient(
      private var apiKeys: List<String>,
      private var modelName: String = "gemini-3-flash-preview"
  ) {
      companion object {
          val MODEL_PRIORITY = listOf(
              "gemini-3-flash-preview",
              "gemini-2.5-flash",
              "gemini-2.5-flash-lite",
              "gemini-flash-latest"
          )
          
          suspend fun testConnection(apiKey: String, modelName: String): Result<String>
          fun isQuotaError(exception: Exception): Boolean
      }
      
      fun getPromptForCopy(data: BaZiData, userInput: UserInput): String
      fun generateReadingStream(data: BaZiData, userInput: UserInput): Flow<String>
  }
  ```

### 2. Thiết kế JSON Prompt cho Bát Tự
- [ ] `com.skul9x.battu.ai.PromptBuilder.kt`
- [ ] Cấu trúc JSON prompt:
  ```json
  {
    "role": "AI chuyên luận Bát Tự Tử Bình (Tứ Trụ)",
    "style": {
      "tone": "Điềm đạm – phân tích mệnh lý. Xưng 'Tôi', gọi người xem 'Bạn'."
    },
    "methodology_sources": ["Tử Bình Chân Thuyên", "Trích Thiên Tùy", "Uyên Hải Tử Bình"],
    "objective": "Phân tích Bát Tự theo cấu trúc Can Chi – không suy đoán cảm tính",
    "absolute_rules": {
      "must_do": [
        "Mọi nhận định BẮT BUỘC phải có căn cứ Can Chi",
        "Phân biệt rõ: Nhật Chủ, Dụng Thần, Hỉ Thần, Kỵ Thần",
        "Phân tích Tàng Can và trọng số ảnh hưởng"
      ],
      "must_not": [
        "Không suy đoán khi thiếu dữ liệu",
        "Không dùng câu chung chung",
        "Không khẳng định tuyệt đối"
      ]
    },
    "analysis_pipeline": [
      "Bước 1: Xác định Nhật Chủ (Day Master) vượng/suy",
      "Bước 2: Tìm Dụng Thần / Hỉ Thần / Kỵ Thần",
      "Bước 3: Phân tích Thập Thần → Tính cách, năng lực",
      "Bước 4: Phân tích Ngũ Hành balance → Sức khỏe",
      "Bước 5: Phân tích quan hệ Can Chi → Sự nghiệp, tài lộc, tình duyên",
      "Bước 6: Tổng hợp và lời khuyên"
    ],
    "output_format": {
      "sections": {
        "A": "Tóm tắt lá số (5-10 dòng)",
        "B": "Phân tích Nhật Chủ vượng suy",
        "C": "Dụng Thần & Hỉ Thần",
        "D": "Phân tích tính cách",
        "E": "Sự nghiệp & Tài lộc",
        "F": "Tình duyên & Gia đình",
        "G": "Sức khỏe",
        "H": "Lời khuyên tổng hợp"
      }
    },
    "chart_data": {
      "person": { "name": "...", "gender": "...", "birth": "..." },
      "pillars": {
        "year": { "stem": "Giáp", "branch": "Tý", ... },
        "month": { ... },
        "day": { ... },
        "hour": { ... }
      },
      "ten_gods": { ... },
      "element_balance": { "Kim": 40, "Mộc": 80, ... },
      "solar_terms": { "current": "...", "next": "..." },
      "nap_am": "Hải Trung Kim"
    }
  }
  ```

### 3. Tạo System Instruction
- [ ] Nghiên cứu và viết system prompt chuyên gia Bát Tự:
  - Vai trò: Chuyên gia Bát Tự Tử Bình
  - Phương pháp: Dựa theo sách kinh điển
  - Phân tích: Vượng Suy, Dụng Thần, Thập Thần
  - Output: Định dạng Markdown rõ ràng

### 4. Implement Streaming Handler
- [ ] Clone logic từ source `generateReadingStream()`:
  - Multi-key rotation
  - Model fallback
  - Flow<String> streaming
  - Error handling

### 5. Copy to Clipboard
- [ ] `getPromptForCopy()` — Copy JSON prompt
- [ ] `getResultForCopy()` — Copy AI kết quả
- [ ] ClipboardManager integration trong ViewModel
- [ ] Toast notification khi copy thành công

### 6. Result Screen Integration
- [ ] `ResultScreen.kt` hiển thị streaming text
- [ ] Simple Markdown rendering (bold, italic, headers, lists)
- [ ] Copy/Share buttons
- [ ] Retry button khi lỗi

### 7. Settings (API Key Management)
- [ ] `SettingsScreen.kt` — Nhập/sửa API key
- [ ] DataStore để lưu API keys
- [ ] Test connection button
- [ ] Model selection dropdown

## Files to Create
| File | Package | Mô tả |
|------|---------|-------|
| `GeminiClient.kt` | `ai` | AI client (clone từ source) |
| `PromptBuilder.kt` | `ai` | JSON prompt builder cho Bát Tự |
| `ResultScreen.kt` | `ui.screens` | Hiển thị kết quả AI |
| `SettingsScreen.kt` | `ui.screens` | Quản lý API key |
| `MarkdownText.kt` | `ui.components` | Render Markdown đơn giản |

## Test Criteria
- [ ] JSON prompt có format đúng và đầy đủ data
- [ ] Copy prompt → Paste vào AI Studio → AI trả lời có ý nghĩa
- [ ] Streaming hiển thị text từng chunk
- [ ] Quota error → Auto switch key/model
- [ ] Network error → Hiển thị thông báo rõ ràng
- [ ] Empty API key → Hiển thị hướng dẫn nhập key

## Notes
- Source `GeminiClient.kt` có 1469 LOC nhưng ~70% là Tử Vi prompt. App mới chỉ cần ~300-400 LOC.
- Prompt Bát Tự đơn giản hơn Tử Vi vì không có 12 cung, chỉ có 4 trụ.
- Cần test kỹ streaming trên thiết bị thật (emulator có thể chậm hơn).

---
Next Phase: [phase-05-history.md](./phase-05-history.md)
