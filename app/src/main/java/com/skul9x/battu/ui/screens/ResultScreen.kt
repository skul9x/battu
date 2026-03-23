package com.skul9x.battu.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skul9x.battu.ui.components.MarkdownText
import com.skul9x.battu.ui.viewmodel.BatTuViewModel

/**
 * ResultScreen - Display AI interpretation result with streaming text
 * 
 * Features:
 * - Streaming text display (character by character)
 * - Markdown rendering (bold, italic, headers, lists)
 * - Copy result to clipboard
 * - Retry on error
 * - Loading state with progress indicator
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    onNavigateBack: () -> Unit,
    viewModel: BatTuViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val aiResult by viewModel.aiResult.collectAsState()
    val isLoadingAI by viewModel.isLoadingAI.collectAsState()
    val aiError by viewModel.aiError.collectAsState()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Luận Giải Bát Tự") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
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
            when {
                // Loading state
                isLoadingAI -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Đang phân tích lá số...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "AI đang luận giải theo phương pháp Tử Bình",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Error state
                aiError != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "❌ Lỗi",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = aiError ?: "Không xác định",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { viewModel.retryAIGeneration() }
                        ) {
                            Text("Thử lại")
                        }
                    }
                }
                
                // Success state - Display result
                aiResult.isNotEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        // Result content with Markdown
                        MarkdownText(
                            markdown = aiResult,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Action buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Copy button
                            OutlinedButton(
                                onClick = { 
                                    clipboardManager.setText(AnnotatedString(aiResult))
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("📋 Copy")
                            }
                            
                            // Share button
                            OutlinedButton(
                                onClick = { 
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, "Luận giải Bát Tự của tôi từ app BatTu:\n\n$aiResult")
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    context.startActivity(shareIntent)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("🔗 Chia sẻ")
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(80.dp)) // Bottom padding
                    }
                }
                
                // Empty state
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Chưa có kết quả",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Vui lòng nhập thông tin và tính toán lá số",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
