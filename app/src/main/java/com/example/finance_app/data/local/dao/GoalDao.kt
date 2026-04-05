package com.example.finance_app.data.local.dao

import androidx.room.*
import com.example.finance_app.data.local.entities.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals WHERE month = :month AND year = :year")
    fun getGoalsByMonth(month: Int, year: Int): Flow<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)
}
