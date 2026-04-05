package com.example.finance_app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finance_app.viewmodel.FinanceViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun GoalsScreen(viewModel: FinanceViewModel) {
    val goals by viewModel.currentMonthGoals.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    
    val currentSavings = totalIncome - totalExpenses
    val activeGoal = goals.firstOrNull()

    var showDialog by remember { mutableStateOf(false) }
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Savings Goals",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (activeGoal == null) {
            EmptyGoalView(onAddClick = { showDialog = true })
        } else {
            GoalProgressCard(
                targetAmount = activeGoal.targetAmount,
                currentSavings = currentSavings,
                formatter = currencyFormatter
            )
            
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Update Goal")
            }
        }
    }

    if (showDialog) {
        SetGoalDialog(
            onDismiss = { showDialog = false },
            onConfirm = { amount ->
                viewModel.setGoal(amount)
                showDialog = false
            }
        )
    }
}

@Composable
fun EmptyGoalView(onAddClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "No goal set for this month", 
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onAddClick) {
                Text("Set Monthly Goal")
            }
        }
    }
}

@Composable
fun GoalProgressCard(targetAmount: Double, currentSavings: Double, formatter: NumberFormat) {
    val progress = (currentSavings / targetAmount).coerceIn(0.0, 1.0).toFloat()
    val animatedProgress by animateFloatAsState(targetValue = progress)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Monthly Savings Progress", 
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = if (progress >= 1f) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Saved", 
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        formatter.format(currentSavings.coerceAtLeast(0.0)), 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "Target", 
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        formatter.format(targetAmount), 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            if (currentSavings >= targetAmount) {
                Text(
                    "Goal Achieved! 🎉",
                    modifier = Modifier.padding(top = 16.dp),
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            } else {
                val remaining = targetAmount - currentSavings
                Text(
                    "Keep going! Only ${formatter.format(remaining)} left.",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun SetGoalDialog(onDismiss: () -> Unit, onConfirm: (Double) -> Unit) {
    var amount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Monthly Savings Goal") },
        text = {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Target Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = { amount.toDoubleOrNull()?.let { onConfirm(it) } }) {
                Text("Set")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
