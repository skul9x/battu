package com.skul9x.battu.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.skul9x.battu.data.local.HistoryEntity
import com.skul9x.battu.ui.Screen
import com.skul9x.battu.ui.viewmodel.BatTuViewModel
import com.skul9x.battu.ui.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * History Screen - Displays a list of previously calculated Bát Tự charts
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: BatTuViewModel,
    historyViewModel: HistoryViewModel = viewModel()
) {
    val historyItems by historyViewModel.historyItems.collectAsState()
    var showDeleteConfirm by remember { mutableStateOf<HistoryEntity?>(null) }
    var showClearConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lịch Sử Lá Số", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    if (historyItems.isNotEmpty()) {
                        IconButton(onClick = { showClearConfirm = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Xóa tất cả", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (historyItems.isEmpty()) {
                EmptyHistoryState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(historyItems, key = { it.id }) { item ->
                        HistoryItemCard(
                            item = item,
                            onClick = {
                                viewModel.loadFromHistory(item)
                                navController.navigate(Screen.Chart.route)
                            },
                            onDelete = { showDeleteConfirm = item }
                        )
                    }
                }
            }

            // Delete Confirmation Dialog
            showDeleteConfirm?.let { entity ->
                AlertDialog(
                    onDismissRequest = { showDeleteConfirm = null },
                    title = { Text("Xóa lịch sử") },
                    text = { Text("Bạn có chắc chắn muốn xóa lá số của '${entity.name}' không?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                historyViewModel.deleteHistory(entity)
                                showDeleteConfirm = null
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Xóa")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteConfirm = null }) {
                            Text("Hủy")
                        }
                    }
                )
            }

            // Clear All Confirmation Dialog
            if (showClearConfirm) {
                AlertDialog(
                    onDismissRequest = { showClearConfirm = false },
                    title = { Text("Xóa tất cả") },
                    text = { Text("Bạn có chắc chắn muốn xóa toàn bộ lịch sử không? Hành động này không thể hoàn tác.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                historyViewModel.clearAllHistory()
                                showClearConfirm = false
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Xóa tất cả")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showClearConfirm = false }) {
                            Text("Hủy")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HistoryItemCard(
    item: HistoryEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()) }
    val displayDate = remember(item.createdAt) { dateFormat.format(Date(item.createdAt)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val genderEmoji = if (item.gender == "NAM") "♂️" else "♀️"
                    Text(
                        text = "$genderEmoji | ${item.birthDate} | Giờ ${item.birthHour}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Tạo lúc: $displayDate",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    fontSize = 10.sp
                )
            }
            
            if (item.aiResult != null) {
                Text(
                    text = "🤖",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Xóa",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyHistoryState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("📚", fontSize = 64.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Chưa có lịch sử lá số",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Hãy lập lá số đầu tiên của bạn!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
