package com.zavetg.diceroller.data

import androidx.lifecycle.LiveData

class DiceRollRepository(private val diceRollDao: DiceRollDao) {
    val allRolls: LiveData<List<DiceRoll>> = diceRollDao.getAllRolls()
    val rollCount: LiveData<Int> = diceRollDao.getRollCount()

    suspend fun insert(roll: DiceRoll) {
        diceRollDao.insert(roll)
    }

    suspend fun delete(roll: DiceRoll) {
        diceRollDao.delete(roll)
    }

    suspend fun deleteAll() {
        diceRollDao.deleteAll()
    }
}
