package com.zavetg.diceroller

import android.app.Application
import com.zavetg.diceroller.data.AppDatabase
import com.zavetg.diceroller.data.DiceRollRepository

class DiceRollerApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { DiceRollRepository(database.diceRollDao()) }
}
