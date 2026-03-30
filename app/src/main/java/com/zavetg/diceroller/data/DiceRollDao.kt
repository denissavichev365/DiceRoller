package com.zavetg.diceroller.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DiceRollDao {
    @Query("SELECT * FROM dice_rolls ORDER BY createdAt DESC")
    fun getAllRolls(): LiveData<List<DiceRoll>>

    @Insert
    suspend fun insert(roll: DiceRoll)

    @Delete
    suspend fun delete(roll: DiceRoll)

    @Query("DELETE FROM dice_rolls")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM dice_rolls")
    fun getRollCount(): LiveData<Int>
}
