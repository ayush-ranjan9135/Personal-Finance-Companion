package com.example.finance_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finance_app.data.local.entities.Category
import com.example.finance_app.data.local.entities.DefaultCategories
import com.example.finance_app.data.local.entities.TransactionType
import com.example.finance_app.viewmodel.FinanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: FinanceViewModel, 
    navController: NavController,
    transactionId: Long? = null
) {
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(DefaultCategories[0]) }
    var notes by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }
    var date by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(transactionId) {
        if (transactionId != null) {
            val transaction = viewModel.getTransactionById(transactionId)
            transaction?.let {
                amount = it.amount.toString()
                notes = it.notes
                type = it.type
                date = it.date
                selectedCategory = DefaultCategories.find { cat -> cat.name == it.category } ?: DefaultCategories[0]
            }
        }
    }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    date = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    showDatePicker = false
                }) { Text("OK") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (transactionId == null) "Add Transaction" else "Edit Transaction", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Type Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp)
            ) {
                listOf(TransactionType.EXPENSE, TransactionType.INCOME).forEach { t ->
                    val isSelected = type == t
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) (if (t == TransactionType.INCOME) Color(0xFF10B981) else Color(0xFFEF4444)) else Color.Transparent)
                            .clickable { type = t },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (t == TransactionType.EXPENSE) "Expense" else "Income",
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Amount Field
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text("Enter Amount", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelLarge)
                TextField(
                    value = amount,
                    onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) amount = it },
                    placeholder = { Text("0.00", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    textStyle = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true
                )
            }

            // Date Picker Trigger
            OutlinedCard(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Date:", modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(date)),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Category Selection
            Text("Category", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(DefaultCategories) { category ->
                    EmojiCategoryItem(
                        category = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            // Notes
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Add a note...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            // Save Button
            Button(
                onClick = {
                    val amountDouble = amount.toDoubleOrNull() ?: 0.0
                    if (amountDouble > 0) {
                        if (transactionId == null) {
                            viewModel.addTransaction(
                                amount = amountDouble,
                                type = type,
                                category = selectedCategory.name,
                                notes = notes,
                                date = date
                            )
                        } else {
                            viewModel.updateTransaction(
                                com.example.finance_app.data.local.entities.Transaction(
                                    id = transactionId,
                                    amount = amountDouble,
                                    type = type,
                                    category = selectedCategory.name,
                                    notes = notes,
                                    date = date
                                )
                            )
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                enabled = amount.isNotBlank(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (transactionId == null) "Confirm Transaction" else "Update Transaction", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun EmojiCategoryItem(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) category.color else MaterialTheme.colorScheme.surfaceVariant)
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text(text = category.emoji, fontSize = 24.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )
    }
}
