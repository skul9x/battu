package com.skul9x.battu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.skul9x.battu.data.Interaction
import com.skul9x.battu.data.LuckPillar
import com.skul9x.battu.data.ShenSha
import com.skul9x.battu.ui.Screen
import com.skul9x.battu.ui.components.ElementBalanceChart
import com.skul9x.battu.ui.components.PillarCard
import com.skul9x.battu.ui.viewmodel.BatTuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(
    navController: NavController,
    viewModel: BatTuViewModel
) {
    val baZiData by viewModel.baZiData.collectAsState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var saveSuccess by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lá Số Tứ Trụ") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Trở về")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (baZiData == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Không có dữ liệu lá số.")
            }
            return@Scaffold
        }
        
        val data = baZiData!!
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // User Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Thông Tin Lá Số",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Sinh vào Tiết: ${data.currentTerm}")
                    Text("Nạp Âm Năm: ${data.year.branchElement} ${data.year.stemElement}")
                }
            }
            
            // Four Pillars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Hour
                PillarCard(
                    title = "Giờ",
                    pillar = data.hour,
                    stemTenGod = data.tenGods.hourStemGod,
                    branchTenGod = data.tenGods.hourBranchGod,
                    modifier = Modifier.weight(1f)
                )
                // Day
                PillarCard(
                    title = "Ngày",
                    pillar = data.day,
                    stemTenGod = "Nhật Chủ", // Handled inside PillarCard
                    branchTenGod = data.tenGods.monthBranchGod, // Using monthBranch for now or empty
                    modifier = Modifier.weight(1f),
                    isDayPillar = true
                )
                // Month
                PillarCard(
                    title = "Tháng",
                    pillar = data.month,
                    stemTenGod = data.tenGods.monthStemGod,
                    branchTenGod = data.tenGods.monthBranchGod,
                    modifier = Modifier.weight(1f)
                )
                // Year
                PillarCard(
                    title = "Năm",
                    pillar = data.year,
                    stemTenGod = data.tenGods.yearStemGod,
                    branchTenGod = data.tenGods.yearBranchGod,
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Season & Strength Info
            if (data.season.isNotEmpty() || data.dayMasterStrength.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Mùa sinh: ${data.season} | Nhật chủ: ${data.dayMasterStrength}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Interactions Section
            if (data.interactions.isNotEmpty()) {
                InteractionsSection(data.interactions)
            }
            
            // Shen Sha Section
            if (data.shenShaList.isNotEmpty()) {
                ShenShaSection(data.shenShaList)
            }
            
            // Luck Pillars Section
            if (data.luckPillars.isNotEmpty()) {
                LuckPillarsSection(data.luckPillars)
            }
            
            // Element Balance
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                ElementBalanceChart(
                    elementCounts = data.elementBalance,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        val json = viewModel.generateJsonPrompt()
                        clipboardManager.setText(AnnotatedString(json))
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(Icons.Default.Info, contentDescription = "Copy")
                    Spacer(Modifier.width(8.dp))
                    Text("Copy JSON")
                }
                
                Button(
                    onClick = {
                        // Trigger AI generation and navigate to ResultScreen
                        viewModel.generateAIInterpretation()
                        navController.navigate(Screen.Result.route)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "AI")
                    Spacer(Modifier.width(8.dp))
                    Text("Gửi AI")
                }
            }
            
            OutlinedButton(
                onClick = { 
                    viewModel.saveToHistory()
                    saveSuccess = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !saveSuccess
            ) {
                Icon(
                    if (saveSuccess) Icons.Default.Check else Icons.Default.Check, 
                    contentDescription = "Save"
                )
                Spacer(Modifier.width(8.dp))
                Text(if (saveSuccess) "Đã lưu vào Lịch sử" else "Lưu Lá Số")
            }
        }
    }
}

@Composable
fun InteractionsSection(interactions: List<Interaction>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Mối Quan Hệ Can Chi (Hợp/Xung)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            interactions.forEach { interaction ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    val color = when {
                        interaction.typeName.contains("Xung") || interaction.typeName.contains("Hại") || interaction.typeName.contains("Hình") -> Color.Red
                        else -> Color(0xFF4CAF50) // Green
                    }
                    Text(
                        "[${interaction.typeName}]",
                        style = MaterialTheme.typography.labelMedium,
                        color = color,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "${interaction.pillars.joinToString(" - ")}: ${interaction.description}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun ShenShaSection(shenShaList: List<ShenSha>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Thần Sát (Divine Stars)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            
            // Group by Pillar
            val grouped = shenShaList.groupBy { it.pillar }
            grouped.forEach { (pillar, list) ->
                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                    Text(
                        "$pillar: ",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        list.joinToString(", ") { it.name },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun LuckPillarsSection(luckPillars: List<LuckPillar>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Bảng Đại Vận (Luck Pillars)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            
            // Simple grid for Luck Pillars
            luckPillars.chunked(5).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEach { lp ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text("${lp.startAge}-${lp.endAge}", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp)
                            Text(lp.stem, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(lp.branch, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
