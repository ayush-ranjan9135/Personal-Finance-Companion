package com.example.finance_app.data.local

import androidx.room.TypeConverter
import com.example.finance_app.data.local.entities.TransactionType

class DataConverters {
    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}
