package com.skul9x.battu.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skul9x.battu.ui.theme.getElementColor

@Composable
fun ElementBalanceChart(
    elementCounts: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    val totalElements = elementCounts.values.sum().coerceAtLeast(1)
    val elements = listOf("Mộc", "Hỏa", "Thổ", "Kim", "Thủy")
    
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Ngũ Hành Balance",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth().height(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val totalWidth = size.width
                var currentX = 0f
                
                elements.forEach { element ->
                    val count = elementCounts[element] ?: 0
                    if (count > 0) {
                        val percentage = count.toFloat() / totalElements
                        val segmentWidth = totalWidth * percentage
                        
                        drawRoundRect(
                            color = getElementColor(element),
                            topLeft = androidx.compose.ui.geometry.Offset(currentX, 0f),
                            size = Size(segmentWidth, size.height),
                            cornerRadius = CornerRadius(4.dp.toPx())
                        )
                        currentX += segmentWidth
                    }
                }
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            elements.forEach { element ->
                val count = elementCounts[element] ?: 0
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ElementBadge(element = element)
                    Text(
                        text = "$count",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
