package com.zavetg.diceroller.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dice_rolls")
data class DiceRoll(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val diceType: Int,       // 4, 6, 8, 10, 12, or 20
    val diceCount: Int,      // 1-5
    val results: String,     // comma-separated results, e.g. "3,5,1"
    val sum: Int,
    val createdAt: Long = System.currentTimeMillis()
)
