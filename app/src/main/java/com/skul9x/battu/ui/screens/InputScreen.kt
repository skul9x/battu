package com.skul9x.battu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.skul9x.battu.data.Gender
import com.skul9x.battu.ui.Screen
import com.skul9x.battu.ui.viewmodel.BatTuViewModel
import java.util.*

/**
 * Input Screen - User enters birth information
 * Material 3 design with Ngũ Hành color scheme
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    navController: NavController,
    viewModel: BatTuViewModel
) {
    val inputState by viewModel.inputState.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // Date picker state
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().apply {
            set(inputState.birthYear, inputState.birthMonth - 1, inputState.birthDay)
        }.timeInMillis
    )
    var showDatePicker by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📝 Nhập Thông Tin Sinh") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Cài đặt"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Name Input
            OutlinedTextField(
                value = inputState.name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text("Họ và Tên") },
                placeholder = { Text("Nguyễn Văn A") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Gender Selection
            Text(
                text = "Giới Tính",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = inputState.gender == Gender.NAM,
                    onClick = { viewModel.updateGender(Gender.NAM) },
                    label = { Text("Nam ♂") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = inputState.gender == Gender.NU,
                    onClick = { viewModel.updateGender(Gender.NU) },
                    label = { Text("Nữ ♀") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Calendar Type Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (inputState.isLunar) "Âm Lịch 🌙" else "Dương Lịch ☀️",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (inputState.isLunar) "Lunar Calendar" else "Solar Calendar",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                        )
                    }
                    Switch(
                        checked = inputState.isLunar,
                        onCheckedChange = { viewModel.toggleCalendarType() }
                    )
                }
            }
            
            // Birth Date Picker
            Text(
                text = "Ngày Sinh",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = String.format(
                        "%02d/%02d/%04d",
                        inputState.birthDay,
                        inputState.birthMonth,
                        inputState.birthYear
                    ),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            // Birth Hour Selection
            Text(
                text = "Giờ Sinh (Theo Can Chi)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Hệ thống tự động lấy điểm giữa (Midpoint) của Giờ Can Chi để tối đa hóa tính chính xác và triệt tiêu sai số.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HourSelector(
                selectedHour = inputState.birthHour,
                onHourSelected = { viewModel.updateBirthHour(it) }
            )
            
            // Advanced Options
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "⚙️ Tùy Chọn Nâng Cao",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    // Longitude Input
                    OutlinedTextField(
                        value = inputState.longitude.toString(),
                        onValueChange = { 
                            it.toDoubleOrNull()?.let { lon -> viewModel.updateLongitude(lon) }
                        },
                        label = { Text("Kinh Độ") },
                        placeholder = { Text("105.8") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Mặc định: 105.8 (Hà Nội)") }
                    )
                    
                    // Day Boundary Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Đổi ngày lúc 23:00",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Theo phong tục Tử Thì",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
                            )
                        }
                        Checkbox(
                            checked = inputState.dayBoundaryAt23,
                            onCheckedChange = { viewModel.toggleDayBoundary() }
                        )
                    }
                }
            }
            
            // Error Display
            if (error != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("⚠️", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = error!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            
            // Calculate Button
            Button(
                onClick = {
                    if (viewModel.calculateBaZi()) {
                        navController.navigate(Screen.Chart.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (isLoading) "Đang tính toán..." else "🎴 Lập Lá Số Bát Tự",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    
    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance().apply {
                                timeInMillis = millis
                            }
                            viewModel.updateBirthDate(
                                year = calendar.get(Calendar.YEAR),
                                month = calendar.get(Calendar.MONTH) + 1,
                                day = calendar.get(Calendar.DAY_OF_MONTH)
                            )
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Hủy")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

/**
 * Hour Selector Component
 * Displays 12 traditional Chinese hours (Canh Giờ)
 */
@Composable
fun HourSelector(
    selectedHour: Int,
    onHourSelected: (Int) -> Unit
) {
    val hours = listOf(
        HourOption(0, "Tý", "23:00 - 00:59"),
        HourOption(2, "Sửu", "01:00 - 02:59"),
        HourOption(4, "Dần", "03:00 - 04:59"),
        HourOption(6, "Mão", "05:00 - 06:59"),
        HourOption(8, "Thìn", "07:00 - 08:59"),
        HourOption(10, "Tỵ", "09:00 - 10:59"),
        HourOption(12, "Ngọ", "11:00 - 12:59"),
        HourOption(14, "Mùi", "13:00 - 14:59"),
        HourOption(16, "Thân", "15:00 - 16:59"),
        HourOption(18, "Dậu", "17:00 - 18:59"),
        HourOption(20, "Tuất", "19:00 - 20:59"),
        HourOption(22, "Hợi", "21:00 - 22:59")
    )
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        hours.chunked(2).forEach { rowHours: List<HourOption> ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowHours.forEach { hour ->
                    val isSelected = selectedHour in hour.startHour..(hour.startHour + 1)
                    FilterChip(
                        selected = isSelected,
                        onClick = { onHourSelected(hour.startHour) },
                        label = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Giờ ${hour.name}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                                Text(
                                    text = hour.timeRange,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

/**
 * Data class for hour options
 */
data class HourOption(
    val startHour: Int,
    val name: String,
    val timeRange: String
)
