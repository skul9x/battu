package com.skul9x.battu.data.local

import androidx.room.*

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String = "",
    val gender: String = "",
    val birthDate: String = "",      // "DD/MM/YYYY"
    val birthHour: Int = 0,
    val chartJson: String = "",      // Serialized BaZiData
    val promptJson: String = "",     // JSON prompt sent to AI
    val aiResult: String? = null,      // AI response text
    val createdAt: Long = System.currentTimeMillis()
)
