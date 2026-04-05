package com.example.finance_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finance_app.data.local.entities.DefaultCategories
import com.example.finance_app.data.local.entities.TransactionType
import com.example.finance_app.ui.components.PieChart
import com.example.finance_app.viewmodel.FinanceViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun InsightsScreen(viewModel: FinanceViewModel) {
    val transactions by viewModel.allTransactions.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()

    val categoryExpenses = transactions
        .filter { it.type == TransactionType.EXPENSE }
        .groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }
        .toList()
        .sortedByDescending { it.second }

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Insights",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (categoryExpenses.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Expense Distribution",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        val chartData = categoryExpenses.toMap()
                        val chartColors = categoryExpenses.map { (catName, _) ->
                            DefaultCategories.find { it.name == catName }?.color ?: Color.Gray
                        }
                        
                        PieChart(data = chartData, colors = chartColors)
                    }
                }
            }
        }

        item {
            Text(
                text = "Spending by Category",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (categoryExpenses.isEmpty()) {
            item {
                Text(
                    "No expense data available for insights.",
                    modifier = Modifier.padding(vertical = 32.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(categoryExpenses) { (category, amount) ->
                CategoryInsightItem(
                    category = category,
                    amount = amount,
                    total = totalExpenses,
                    formatter = currencyFormatter
                )
            }
        }
    }
}

@Composable
fun CategoryInsightItem(category: String, amount: Double, total: Double, formatter: NumberFormat) {
    val percentage = if (total > 0) (amount / total).toFloat() else 0f
    val categoryColor = DefaultCategories.find { it.name == category }?.color ?: MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formatter.format(amount),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentage)
                        .fillMaxHeight()
                        .background(categoryColor)
                )
            }
            Text(
                text = "${(percentage * 100).toInt()}% of total expenses",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
