package com.skul9x.battu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skul9x.battu.ui.viewmodel.BatTuViewModel

/**
 * SettingsScreen - Manage API keys and model selection
 * 
 * Features:
 * - Add/remove API keys
 * - Test connection for each key
 * - Select preferred model
 * - Save settings to DataStore
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: BatTuViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val apiKeys by viewModel.apiKeys.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()
    
    var showAddKeyDialog by remember { mutableStateOf(false) }
    var newApiKey by remember { mutableStateOf("") }
    var testingKeyIndex by remember { mutableStateOf<Int?>(null) }
    var testResult by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cài đặt") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
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
            // API Keys section
            Text(
                text = "API Keys",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Nhập API Key từ Google AI Studio (https://aistudio.google.com/apikey)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // List of API keys
            if (apiKeys.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Chưa có API Key",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                apiKeys.forEachIndexed { index, key ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Key ${index + 1}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = maskApiKey(key),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                // Test button
                                OutlinedButton(
                                    onClick = {
                                        testingKeyIndex = index
                                        viewModel.testApiKey(key, selectedModel) { result ->
                                            testResult = result
                                            testingKeyIndex = null
                                        }
                                    },
                                    enabled = testingKeyIndex != index
                                ) {
                                    if (testingKeyIndex == index) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(16.dp),
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Text("Test")
                                    }
                                }
                                
                                // Delete button
                                IconButton(
                                    onClick = { viewModel.removeApiKey(index) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Xóa",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Add key button
            Button(
                onClick = { showAddKeyDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Thêm API Key")
            }
            
            // Test result
            if (testResult != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (testResult!!.contains("thành công")) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = testResult!!,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            
            // Model selection
            Text(
                text = "Model",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Chọn model AI (tự động fallback nếu quota hết)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Model dropdown
            val models = listOf(
                "gemini-3-flash-preview",
                "gemini-2.5-flash",
                "gemini-2.5-flash-lite",
                "gemini-flash-latest"
            )
            
            var expanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedModel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Model") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    models.forEach { model ->
                        DropdownMenuItem(
                            text = { Text(model) },
                            onClick = {
                                viewModel.updateSelectedModel(model)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Add API Key Dialog
    if (showAddKeyDialog) {
        AlertDialog(
            onDismissRequest = { showAddKeyDialog = false },
            title = { Text("Thêm API Key") },
            text = {
                OutlinedTextField(
                    value = newApiKey,
                    onValueChange = { newApiKey = it },
                    label = { Text("API Key") },
                    placeholder = { Text("AIza...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newApiKey.isNotBlank()) {
                            viewModel.addApiKey(newApiKey)
                            newApiKey = ""
                            showAddKeyDialog = false
                        }
                    }
                ) {
                    Text("Thêm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddKeyDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}

/**
 * Mask API key for display (show first 8 and last 4 characters)
 */
private fun maskApiKey(key: String): String {
    return if (key.length > 12) {
        "${key.take(8)}...${key.takeLast(4)}"
    } else {
        key
    }
}
