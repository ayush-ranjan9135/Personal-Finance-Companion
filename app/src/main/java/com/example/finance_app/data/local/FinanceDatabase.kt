package com.example.finance_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finance_app.data.local.dao.GoalDao
import com.example.finance_app.data.local.dao.TransactionDao
import com.example.finance_app.data.local.entities.Goal
import com.example.finance_app.data.local.entities.Transaction

@Database(entities = [Transaction::class, Goal::class], version = 1, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun goalDao(): GoalDao

    companion object {
        @Volatile
        private var INSTANCE: FinanceDatabase? = null

        fun getDatabase(context: Context): FinanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinanceDatabase::class.java,
                    "finance_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
