package com.example.finance_app.data.repository

import com.example.finance_app.data.local.dao.GoalDao
import com.example.finance_app.data.local.dao.TransactionDao
import com.example.finance_app.data.local.entities.Goal
import com.example.finance_app.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

class FinanceRepository(
    private val transactionDao: TransactionDao,
    private val goalDao: GoalDao
) {
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    fun getGoalsByMonth(month: Int, year: Int): Flow<List<Goal>> =
        goalDao.getGoalsByMonth(month, year)

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun getTransactionById(id: Long): Transaction? {
        return transactionDao.getTransactionById(id)
    }

    suspend fun insertGoal(goal: Goal) {
        goalDao.insertGoal(goal)
    }

    suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal)
    }
}
