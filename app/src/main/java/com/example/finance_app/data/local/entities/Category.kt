package com.example.finance_app.data.local.entities

import androidx.compose.ui.graphics.Color

data class Category(
    val name: String,
    val emoji: String,
    val color: Color
)

val DefaultCategories = listOf(
    Category("Food", "🍔", Color(0xFFFF9800)),
    Category("Transport", "🚗", Color(0xFF2196F3)),
    Category("Shopping", "🛍️", Color(0xFFE91E63)),
    Category("Salary", "💰", Color(0xFF4CAF50)),
    Category("Bills", "🧾", Color(0xFFF44336)),
    Category("Health", "🏥", Color(0xFF00BCD4)),
    Category("Entertainment", "🎬", Color(0xFF9C27B0)),
    Category("Others", "📦", Color(0xFF607D8B))
)
