package com.example.finance_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finance_app.ui.navigation.Screen
import com.example.finance_app.ui.screens.*
import com.example.finance_app.ui.theme.FinanceappTheme
import com.example.finance_app.viewmodel.FinanceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceappTheme {
                val viewModel: FinanceViewModel = viewModel()
                val navController = rememberNavController()
                
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                val showBottomBar = currentDestination?.route?.startsWith("edit_transaction") == false && 
                                    currentDestination?.route != Screen.AddTransaction.route

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                val items = listOf(
                                    Screen.Home,
                                    Screen.Transactions,
                                    Screen.Insights,
                                    Screen.Goals
                                )
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        icon = { Icon(screen.icon, contentDescription = null) },
                                        label = { Text(screen.title) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        if (showBottomBar) {
                            FloatingActionButton(
                                onClick = { navController.navigate(Screen.AddTransaction.route) },
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) { HomeScreen(viewModel) }
                        composable(Screen.Transactions.route) { 
                            TransactionsScreen(viewModel, onTransactionClick = { id ->
                                navController.navigate("edit_transaction/$id")
                            }) 
                        }
                        composable(Screen.Insights.route) { InsightsScreen(viewModel) }
                        composable(Screen.Goals.route) { GoalsScreen(viewModel) }
                        composable(Screen.AddTransaction.route) {
                            AddTransactionScreen(viewModel, navController)
                        }
                        composable(
                            route = Screen.EditTransaction.route,
                            arguments = listOf(navArgument("transactionId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val transactionId = backStackEntry.arguments?.getLong("transactionId")
                            AddTransactionScreen(viewModel, navController, transactionId)
                        }
                    }
                }
            }
        }
    }
}
