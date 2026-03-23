package com.skul9x.battu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skul9x.battu.data.Pillar
import com.skul9x.battu.ui.theme.getElementColorVariant

@Composable
fun PillarCard(
    title: String,
    pillar: Pillar,
    stemTenGod: String?,
    branchTenGod: String?,
    modifier: Modifier = Modifier,
    isDayPillar: Boolean = false
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Title (Year, Month, Day, Hour)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Stem Ten God (or "Nhật Chủ" for day stem)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isDayPillar) "Nhật Chủ" else (stemTenGod ?: ""),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isDayPillar) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (isDayPillar) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
            
            // Heavenly Stem (Thiên Can)
            val stemColor = getElementColorVariant(pillar.stemElement, "light")
            val stemTextColor = getElementColorVariant(pillar.stemElement, "dark")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(stemColor)
                    .border(1.dp, stemTextColor, RoundedCornerShape(4.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = pillar.stem,
                        style = MaterialTheme.typography.titleMedium,
                        color = stemTextColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "(${pillar.stemElement})",
                        style = MaterialTheme.typography.labelSmall,
                        color = stemTextColor
                    )
                }
            }
            
            // Branch Ten God
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = branchTenGod ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
            
            // Earthly Branch (Địa Chi)
            val branchColor = getElementColorVariant(pillar.branchElement, "light")
            val branchTextColor = getElementColorVariant(pillar.branchElement, "dark")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(branchColor)
                    .border(1.dp, branchTextColor, RoundedCornerShape(4.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = pillar.branch,
                        style = MaterialTheme.typography.titleMedium,
                        color = branchTextColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "(${pillar.branchElement})",
                        style = MaterialTheme.typography.labelSmall,
                        color = branchTextColor
                    )
                }
            }
            
            // Life Stage (Vòng Trường Sinh)
            if (pillar.lifeStage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pillar.lifeStage,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            // Hidden Stems (Tàng Can)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                pillar.hiddenStems.forEach { hiddenStem ->
                    Text(
                        text = "${hiddenStem.stem} (${hiddenStem.percentage}%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (hiddenStem.type.name == "BAN_KHI") 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (hiddenStem.type.name == "BAN_KHI") FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                }
            }

            // Nạp Âm (Nap Am)
            if (pillar.napAm != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f))
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pillar.napAm.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

// Add these imports to the top of the file
// import androidx.compose.ui.unit.sp
