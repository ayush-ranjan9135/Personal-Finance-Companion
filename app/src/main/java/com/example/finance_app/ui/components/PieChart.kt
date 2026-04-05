package com.example.finance_app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    data: Map<String, Double>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val total = data.values.sum()
    var animationPlayed by remember { mutableStateOf(false) }
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Canvas(
        modifier = modifier
            .size(200.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height
        val thickness = 30.dp.toPx()
        
        var startAngle = -90f

        data.values.forEachIndexed { index, value ->
            val sweepAngle = (value.toFloat() / total.toFloat()) * 360f * animateSize
            drawArc(
                color = colors.getOrElse(index) { Color.Gray },
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = thickness),
                size = Size(width, height)
            )
            startAngle += sweepAngle
        }
    }
}
