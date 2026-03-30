package com.zavetg.diceroller.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zavetg.diceroller.data.DiceRoll
import com.zavetg.diceroller.data.DiceRollRepository
import kotlinx.coroutines.launch

class DiceViewModel(private val repository: DiceRollRepository) : ViewModel() {

    val allRolls: LiveData<List<DiceRoll>> = repository.allRolls

    fun insertRoll(diceType: Int, diceCount: Int, results: List<Int>, sum: Int) {
        viewModelScope.launch {
            val roll = DiceRoll(
                diceType = diceType,
                diceCount = diceCount,
                results = results.joinToString(","),
                sum = sum
            )
            repository.insert(roll)
        }
    }

    fun deleteAllRolls() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}

class DiceViewModelFactory(private val repository: DiceRollRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
