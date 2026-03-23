package com.skul9x.battu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skul9x.battu.ui.theme.getElementColor

@Composable
fun ElementBadge(
    element: String,
    modifier: Modifier = Modifier,
    text: String = element.take(1)
) {
    val color = getElementColor(element)
    
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        )
    }
}
