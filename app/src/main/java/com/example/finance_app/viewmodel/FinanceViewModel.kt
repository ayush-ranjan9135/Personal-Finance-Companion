package com.example.finance_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.finance_app.data.local.FinanceDatabase
import com.example.finance_app.data.local.entities.Goal
import com.example.finance_app.data.local.entities.Transaction
import com.example.finance_app.data.local.entities.TransactionType
import com.example.finance_app.data.repository.FinanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FinanceRepository
    val allTransactions: StateFlow<List<Transaction>>

    init {
        val database = FinanceDatabase.getDatabase(application)
        repository = FinanceRepository(database.transactionDao(), database.goalDao())
        allTransactions = repository.allTransactions.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }

    val currentBalance: StateFlow<Double> = allTransactions.combine(MutableStateFlow(0.0)) { transactions, _ ->
        transactions.sumOf { if (it.type == TransactionType.INCOME) it.amount else -it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalIncome: StateFlow<Double> = allTransactions.combine(MutableStateFlow(0.0)) { transactions, _ ->
        transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalExpenses: StateFlow<Double> = allTransactions.combine(MutableStateFlow(0.0)) { transactions, _ ->
        transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val weeklyExpenses: StateFlow<List<Double>> = allTransactions.combine(MutableStateFlow(0.0)) { transactions, _ ->
        val calendar = Calendar.getInstance()
        val currentWeek = mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfWeek = calendar.timeInMillis

        transactions.filter { it.type == TransactionType.EXPENSE && it.date >= startOfWeek }.forEach { trans ->
            val transCal = Calendar.getInstance()
            transCal.timeInMillis = trans.date
            val dayOfWeek = transCal.get(Calendar.DAY_OF_WEEK)
            val index = if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - 2
            if (index in 0..6) {
                currentWeek[index] += trans.amount
            }
        }
        currentWeek
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), List(7) { 0.0 })

    fun addTransaction(amount: Double, type: TransactionType, category: String, notes: String, date: Long) {
        viewModelScope.launch {
            repository.insertTransaction(Transaction(amount = amount, type = type, category = category, notes = notes, date = date))
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.updateTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    suspend fun getTransactionById(id: Long): Transaction? {
        return repository.getTransactionById(id)
    }

    // Goals handling
    private val calendar = Calendar.getInstance()
    val currentMonthGoals: StateFlow<List<Goal>> = repository.getGoalsByMonth(
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.YEAR)
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setGoal(targetAmount: Double) {
        viewModelScope.launch {
            repository.insertGoal(
                Goal(
                    targetAmount = targetAmount,
                    month = calendar.get(Calendar.MONTH),
                    year = calendar.get(Calendar.YEAR)
                )
            )
        }
    }
}
