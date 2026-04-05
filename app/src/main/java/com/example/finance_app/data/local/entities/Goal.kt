package com.example.finance_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val month: Int,
    val year: Int,
    val category: String? = null
)
