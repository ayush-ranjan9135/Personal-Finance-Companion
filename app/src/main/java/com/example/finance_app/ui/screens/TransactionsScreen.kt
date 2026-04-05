package com.example.finance_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.finance_app.data.local.entities.Transaction
import com.example.finance_app.data.local.entities.TransactionType
import com.example.finance_app.ui.components.TransactionItem
import com.example.finance_app.viewmodel.FinanceViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    viewModel: FinanceViewModel,
    onTransactionClick: (Long) -> Unit
) {
    val transactions by viewModel.allTransactions.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }

    val filteredTransactions = transactions.filter {
        (selectedType == null || it.type == selectedType) &&
        (it.category.contains(searchQuery, ignoreCase = true) ||
                it.notes.contains(searchQuery, ignoreCase = true))
    }

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholder = { Text("Search category or notes...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedType == null,
                onClick = { selectedType = null },
                label = { Text("All") }
            )
            FilterChip(
                selected = selectedType == TransactionType.INCOME,
                onClick = { selectedType = TransactionType.INCOME },
                label = { Text("Income") }
            )
            FilterChip(
                selected = selectedType == TransactionType.EXPENSE,
                onClick = { selectedType = TransactionType.EXPENSE },
                label = { Text("Expenses") }
            )
        }

        if (filteredTransactions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "No transactions found", 
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredTransactions, key = { it.id }) { transaction ->
                    SwipeToDeleteContainer(
                        item = transaction,
                        onDelete = { viewModel.deleteTransaction(transaction) }
                    ) {
                        TransactionItem(
                            transaction = transaction, 
                            formatter = currencyFormatter,
                            modifier = Modifier.clickable { onTransactionClick(transaction.id) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteContainer(
    item: Transaction,
    onDelete: (Transaction) -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete(item)
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = when (dismissState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                else -> Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(color),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        },
        enableDismissFromStartToEnd = false,
        content = { content() }
    )
}
