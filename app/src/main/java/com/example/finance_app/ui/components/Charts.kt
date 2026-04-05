package com.example.finance_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun WeeklyBarChart(
    weeklyData: List<Double>,
    modifier: Modifier = Modifier
) {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")
    val maxAmount = (weeklyData.maxOrNull() ?: 1.0).coerceAtLeast(1.0)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        weeklyData.forEachIndexed { index, amount ->
            val barHeightPercentage = (amount / maxAmount).toFloat()
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .fillMaxHeight(barHeightPercentage.coerceAtLeast(0.05f))
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = days[index],
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}
