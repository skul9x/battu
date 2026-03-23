# Phase 05: History & Polish
Status: ⬜ Pending
Dependencies: Phase 03, Phase 04

## Objective
Thêm tính năng lưu lịch sử lá số, tối ưu UX, và hoàn thiện app.

## Requirements

### Functional
- [ ] Lưu lá số + kết quả AI vào Room Database
- [ ] Xem lại lịch sử (danh sách + chi tiết)
- [ ] Xóa lá số khỏi lịch sử
- [ ] Share kết quả (text/image)
- [ ] Copy kết quả AI

### Non-Functional
- [ ] App icon đẹp (Bát Quái / Ngũ Hành theme)
- [ ] Splash screen
- [ ] Dark/Light theme toggle
- [ ] Smooth animations

## Implementation Steps

### 1. Room Database Setup
- [ ] `HistoryEntity.kt`:
  ```kotlin
  @Entity(tableName = "history")
  data class HistoryEntity(
      @PrimaryKey(autoGenerate = true) val id: Int = 0,
      val name: String,
      val gender: String,
      val birthDate: String,      // "DD/MM/YYYY"
      val birthHour: Int,
      val chartJson: String,      // Serialized BaZiData
      val promptJson: String,     // JSON prompt sent to AI
      val aiResult: String?,      // AI response text
      val createdAt: Long = System.currentTimeMillis()
  )
  ```
- [ ] `HistoryDao.kt`:
  ```kotlin
  @Dao
  interface HistoryDao {
      @Query("SELECT * FROM history ORDER BY createdAt DESC")
      fun getAllHistory(): Flow<List<HistoryEntity>>
      
      @Insert
      suspend fun insert(entity: HistoryEntity)
      
      @Delete
      suspend fun delete(entity: HistoryEntity)
      
      @Query("DELETE FROM history")
      suspend fun deleteAll()
  }
  ```
- [ ] `HistoryDatabase.kt` — Room database class

### 2. History Screen
- [ ] `HistoryScreen.kt`:
  - LazyColumn danh sách lá số
  - Card hiển thị: Tên, Ngày sinh, Thời gian tạo
  - Swipe-to-delete
  - Click → Xem chi tiết (Chart + AI result)
  - Empty state khi chưa có lịch sử

### 3. Save/Load Flow
- [ ] Tự động lưu sau khi AI trả kết quả
- [ ] Option lưu thủ công (chỉ chart, không AI)
- [ ] Load lá số từ history → Hiển thị ChartScreen

### 4. Share Feature
- [ ] Share text: Tứ Trụ + Thập Thần + AI summary
- [ ] Share intent: `Intent.ACTION_SEND`

### 5. App Icon & Branding
- [ ] Thiết kế Adaptive Icon (Bát Quái / Ngũ Hành motif)
- [ ] Splash screen (logo + app name)
- [ ] App name: "Bát Tự" trong strings.xml

### 6. Theme Polish
- [ ] Dark mode hoàn chỉnh
- [ ] Light mode hoàn chỉnh
- [ ] Theme toggle trong Settings
- [ ] System theme follow option

### 7. Navigation Polish
- [ ] Bottom Navigation Bar:
  - 🏠 Lập Lá Số (InputScreen)
  - 📋 Lịch sử (HistoryScreen)
  - ⚙️ Cài đặt (SettingsScreen)
- [ ] Animated navigation transitions

### 8. Final Testing
- [ ] Test trên thiết bị thật (Android 7.0+)
- [ ] Test landscape mode
- [ ] Test với nhiều kích thước màn hình
- [ ] Test offline mode (hiển thị lá số, không gọi AI)
- [ ] Test lưu/xóa lịch sử
- [ ] Performance profiling

## Files to Create/Modify
| File | Package | Mô tả |
|------|---------|-------|
| `HistoryEntity.kt` | `data.local` | Room entity |
| `HistoryDao.kt` | `data.local` | DAO interface |
| `HistoryDatabase.kt` | `data.local` | Room database |
| `HistoryScreen.kt` | `ui.screens` | Màn hình lịch sử |
| `HistoryViewModel.kt` | `ui.viewmodel` | State management |
| Adaptive Icon | `res/mipmap` | App icon |
| `SplashScreen.kt` | `ui.screens` | Splash screen |

## Test Criteria
- [ ] Lưu lá số → Tắt app → Mở lại → Lịch sử vẫn còn
- [ ] Xóa lá số → Biến mất khỏi danh sách
- [ ] Share → App khác nhận được text đúng
- [ ] Dark mode → Tất cả UI elements hiển thị tốt
- [ ] App launch → Splash → InputScreen < 2 giây

## Notes
- Room database version = 1 (không cần migration ở v1.0)
- Lưu `chartJson` dạng serialized JSON để dễ restore
- Cân nhắc limit lịch sử (100 entries?) để tránh DB quá lớn

---
**🎉 HOÀN THÀNH TOÀN BỘ 5 PHASES → App sẵn sàng deploy!**
