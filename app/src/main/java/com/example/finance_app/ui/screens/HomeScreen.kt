package com.example.finance_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.finance_app.ui.components.TransactionItem
import com.example.finance_app.ui.components.WeeklyBarChart
import com.example.finance_app.ui.theme.CardGradient
import com.example.finance_app.ui.theme.ExpenseRed
import com.example.finance_app.ui.theme.IncomeGreen
import com.example.finance_app.viewmodel.FinanceViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun HomeScreen(viewModel: FinanceViewModel) {
    val balance by viewModel.currentBalance.collectAsState()
    val income by viewModel.totalIncome.collectAsState()
    val expenses by viewModel.totalExpenses.collectAsState()
    val transactions by viewModel.allTransactions.collectAsState()
    val weeklyData by viewModel.weeklyExpenses.collectAsState()

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Welcome Back! 👋",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "My Finances",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 2.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("👤", fontSize = 24.sp)
                        }
                    }
                }
            }

            item {
                BalanceCard(balance, income, expenses, currencyFormatter)
            }

            item {
                Text(
                    text = "Weekly Activity 📊",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        WeeklyBarChart(weeklyData = weeklyData)
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions 💸",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { /* Navigate to All */ }) {
                        Text("See All", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            if (transactions.isEmpty()) {
                item {
                    EmptyState()
                }
            } else {
                items(transactions.take(5)) { transaction ->
                    TransactionItem(transaction, currencyFormatter)
                }
            }
        }
    }
}

@Composable
fun BalanceCard(balance: Double, income: Double, expenses: Double, formatter: NumberFormat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(CardGradient)
                .padding(28.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total Balance",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = formatter.format(balance),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FinanceStatInverted(label = "Income", amount = formatter.format(income), icon = "🔼")
                    VerticalDivider(color = Color.White.copy(alpha = 0.2f), modifier = Modifier.height(40.dp).width(1.dp))
                    FinanceStatInverted(label = "Expenses", amount = formatter.format(expenses), icon = "🔽")
                }
            }
        }
    }
}

@Composable
fun FinanceStatInverted(label: String, amount: String, icon: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
            Text(text = amount, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("✨", fontSize = 60.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Ready to track your growth?",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Add your first transaction to see the magic happen.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
