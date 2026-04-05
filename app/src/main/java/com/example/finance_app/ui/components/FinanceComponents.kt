package com.example.finance_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finance_app.data.local.entities.DefaultCategories
import com.example.finance_app.data.local.entities.Transaction
import com.example.finance_app.data.local.entities.TransactionType
import com.example.finance_app.ui.theme.ExpenseRed
import com.example.finance_app.ui.theme.IncomeGreen
import java.text.NumberFormat

@Composable
fun TransactionItem(
    transaction: Transaction,
    formatter: NumberFormat,
    modifier: Modifier = Modifier
) {
    val category = DefaultCategories.find { it.name == transaction.category }
    val emoji = category?.emoji ?: "❓"
    val categoryColor = category?.color ?: Color.Gray

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(categoryColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 24.sp)
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column {
                    Text(
                        text = transaction.category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (transaction.notes.isNotBlank()) {
                        Text(
                            text = transaction.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }
            
            Text(
                text = (if (transaction.type == TransactionType.INCOME) "+" else "-") + formatter.format(transaction.amount),
                color = if (transaction.type == TransactionType.INCOME) IncomeGreen else ExpenseRed,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
