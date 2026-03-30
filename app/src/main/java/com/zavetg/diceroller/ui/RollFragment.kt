package com.zavetg.diceroller.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zavetg.diceroller.DiceRollerApplication
import com.zavetg.diceroller.R
import com.zavetg.diceroller.databinding.FragmentRollBinding

class RollFragment : Fragment() {

    private var _binding: FragmentRollBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiceViewModel by viewModels {
        DiceViewModelFactory((requireActivity().application as DiceRollerApplication).repository)
    }

    private var selectedDiceType = 6
    private var diceCount = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRollBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDiceTypeSelection()
        setupDiceCount()
        setupRollButton()
        restoreSettingsFromArguments()
    }

    private fun restoreSettingsFromArguments() {
        val argDiceType = arguments?.getInt("diceType", 0) ?: 0
        val argDiceCount = arguments?.getInt("diceCount", 0) ?: 0

        if (argDiceType > 0 && argDiceCount > 0) {
            selectedDiceType = argDiceType
            diceCount = argDiceCount

            val chipId = when (argDiceType) {
                4 -> R.id.chipD4
                6 -> R.id.chipD6
                8 -> R.id.chipD8
                10 -> R.id.chipD10
                12 -> R.id.chipD12
                20 -> R.id.chipD20
                else -> R.id.chipD6
            }
            binding.chipGroupDiceType.check(chipId)
            updateDiceCountDisplay()

            arguments?.clear()
        }
    }

    private fun setupDiceTypeSelection() {
        binding.chipGroupDiceType.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                selectedDiceType = when (checkedIds[0]) {
                    R.id.chipD4 -> 4
                    R.id.chipD6 -> 6
                    R.id.chipD8 -> 8
                    R.id.chipD10 -> 10
                    R.id.chipD12 -> 12
                    R.id.chipD20 -> 20
                    else -> 6
                }
            }
        }
    }

    private fun setupDiceCount() {
        updateDiceCountDisplay()

        binding.btnMinus.setOnClickListener {
            if (diceCount > 1) {
                diceCount--
                updateDiceCountDisplay()
            }
        }

        binding.btnPlus.setOnClickListener {
            if (diceCount < 5) {
                diceCount++
                updateDiceCountDisplay()
            }
        }
    }

    private fun updateDiceCountDisplay() {
        binding.tvDiceCount.text = diceCount.toString()
        binding.btnMinus.isEnabled = diceCount > 1
        binding.btnPlus.isEnabled = diceCount < 5
    }

    private fun setupRollButton() {
        binding.btnRoll.setOnClickListener {
            val results = mutableListOf<Int>()
            for (i in 1..diceCount) {
                results.add((1..selectedDiceType).random())
            }
            val sum = results.sum()

            binding.cardResults.visibility = View.VISIBLE
            binding.cardResults.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
            )

            binding.tvResults.text = results.joinToString("  ")
            binding.tvSum.text = getString(R.string.sum_label, sum)

            viewModel.insertRoll(selectedDiceType, diceCount, results, sum)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
