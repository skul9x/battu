# Phase 05: History & HistoryScreen Migration

## Goals
Đảm bảo hệ thống có thể đọc được dữ liệu cũ (Legacy Data) từ Database sau khi Engine đã được nâng cấp lên cấu trúc dữ liệu mới. Tránh tình trạng crash hoặc lỗi "decode failed" khi người dùng mở lịch sử.

## Tasks

### 1. Database Compatibility Check
- [ ] Soi lại `HistoryEntity` và `BaZiData`.
- [ ] Kiểm tra các trường mới thêm vào `BaZiData` đã có default value chưa.
- [ ] Cấu hình `kotlinx.serialization.json.Json` để bỏ qua các trường không xác định (`ignoreUnknownKeys = true`).

### 2. Migration Logic (nếu cần)
- [ ] Nếu cấu trúc thay đổi quá lớn, viết hàm `migrateLegacyBaZiData` để map dữ liệu cũ sang mới.
- [ ] Hiện tại có vẻ chỉ là thêm trường nên chỉ cần cấu hình Json linh hoạt là đủ.

### 3. UI/UX Polish for Old Data
- [ ] Kiểm tra màn hình `HistoryScreen` và `ChartScreen` khi hiển thị lá số cũ (thiếu Thần Sát, Đại Vận...).
- [ ] Đảm bảo UI không bị trống huơ trống hoác hoặc lỗi layout khi các list (`interactions`, `shenShaList`) bị rỗng.

### 4. Unit Testing
- [ ] Viết test case simulate load một JSON cũ vào `BaZiData` mới.
- [ ] Verify xem các trường defaults (`emptyList()`, `""`) có hoạt động đúng không.

## Completion Criteria
- [ ] Có thể load lá số cũ từ Lịch sử mà không bị báo lỗi.
- [ ] Lá số mới được lưu với đầy đủ dữ liệu nâng cao (Phase 01-04).
- [ ] Không có crash khi chuyển tab hoặc xóa lịch sử.
