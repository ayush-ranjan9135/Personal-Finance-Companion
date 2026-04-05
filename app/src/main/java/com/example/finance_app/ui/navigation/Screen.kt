package com.example.finance_app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Transactions : Screen("transactions", "Transactions", Icons.AutoMirrored.Filled.List)
    object Insights : Screen("insights", "Insights", Icons.Default.Info)
    object Goals : Screen("goals", "Goals", Icons.Default.Star)
    object AddTransaction : Screen("add_transaction", "Add", Icons.Default.Home)
    object EditTransaction : Screen("edit_transaction/{transactionId}", "Edit", Icons.Default.Home)
}
