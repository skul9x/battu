# Bát Tự AI

Dự án Bát Tự AI là một ứng dụng Android (được viết bằng Kotlin và Jetpack Compose) nhằm cung cấp giải pháp lập lá số Bát Tự (Tứ Trụ) và phân tích các thông số mệnh lý tự động dựa trên trí tuệ nhân tạo (Generative AI - Google Gemini).

## Tính năng nổi bật
*   **Lập Lá Số Bát Tự:** Tính toán và hiển thị Tứ Trụ (Năm, Tháng, Ngày, Giờ sinh), Can Chi Dụng Thần, Hỉ Kỵ Thần.
*   **Lập Đại Vận & Lưu Niên:** Tuổi khởi vận chi tiết (Năm, Tháng, Ngày).
*   **Xác định Lực Lượng Ngũ Hành:** So sánh và đánh giá vượng/suy của Nhật Chủ, tương tác Sinh Khắc (Tam Hợp, Bán Tam Hợp, Lục Xung, Lục Hợp...).
*   **Thần Sát:** Tra cứu hệ thống Thần Sát cát hung, bao gồm Thiên Ất Quý Nhân cho từng trụ, Hoa Cái, Kiếp Sát, Thập Ác Đại Bại...
*   **AI Trợ Lý (Gemini):** Đưa ra các kiến giải và luận giải khoa học (dựa vào cấu hình prompt được điều hướng nghiêm ngặt theo các sách Tử Bình kinh điển).

## Công nghệ sử dụng
*   Kotlin 2.0.20
*   Jetpack Compose (Trải nghiệm người dùng tương tác, mượt mà chuẩn Material 3)
*   Room Database KSP (Quản lý lưu trữ tệp hồ sơ cá nhân)
*   Google Generative AI SDK (Gemini)

## Hướng dẫn cài đặt
1.  **Clone mã nguồn:** `git clone https://github.com/skul9x/battu.git`
2.  Mở dự án trong **Android Studio (Bản mới nhất có hỗ trợ Kotlin 2.0.x)**.
3.  Khi Gradle build xong, chạy ứng dụng trên máy ảo (Emulator) hoặc điện thoại thực.
4.  Cần cung cấp API Key của Google Gemini trong giao diện **Settings** (Cài đặt) để tính năng luận giải tự động với AI được kích hoạt.

## Cấu trúc thư mục (Tham khảo)
*   `app/src/main/java/com/skul9x/battu/data`: Bao gồm Models (Pillar, Interaction, LuckPillar...) và Database DAOs.
*   `app/src/main/java/com/skul9x/battu/core`: Chứa bộ Engine `BaZiLogic.kt` và hằng số `BaZiConstants.kt` để thực thi logic mệnh lý chuẩn.
*   `app/src/main/java/com/skul9x/battu/ai`: `PromptBuilder.kt` đóng vai trò gộp thông tin người dùng và config cấu trúc đầu ra gửi lên AI.
*   `docs/plans`: Tài liệu phân tích kế hoạch (Phases), bug fix và định hướng nâng cấp.
*   `.brain`: Chứa bộ nhớ và state lịch sử của assistant đã thiết lập theo thiết kế.

## Hướng dẫn sử dụng
*   **Bắt đầu lập lá số:** Điền Tên, Ngày Tháng Năm Sinh, Giờ Sinh, Giới Tính (Nam/Nữ).
*   **Lưu kết quả:** Chuyển qua tab History (Lịch sử) để theo dõi lại các lá số đã lưu và kết quả do AI phân tích.
*   **Chia sẻ:** Nhanh chóng tạo tệp văn bản từ AI và chia sẻ ra các ứng dụng nhắn tin khác.

---

Bản quyền Copyright 2026 Nguyễn Duy Trường.
Được xây dựng với mục tiêu giúp những người yêu thích mệnh lý tiếp cận lý thuyết Tử Bình một cách khoa học và hệ thống hóa.
