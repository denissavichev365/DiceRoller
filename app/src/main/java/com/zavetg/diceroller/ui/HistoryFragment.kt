package com.zavetg.diceroller.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zavetg.diceroller.DiceRollerApplication
import com.zavetg.diceroller.R
import com.zavetg.diceroller.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiceViewModel by viewModels {
        DiceViewModelFactory((requireActivity().application as DiceRollerApplication).repository)
    }

    private lateinit var adapter: DiceRollAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DiceRollAdapter { roll ->
            val bundle = bundleOf(
                "diceType" to roll.diceType,
                "diceCount" to roll.diceCount
            )
            findNavController().navigate(R.id.action_history_to_roll, bundle)
            Toast.makeText(
                requireContext(),
                getString(R.string.settings_restored, roll.diceCount, roll.diceType),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.recyclerHistory.adapter = adapter

        viewModel.allRolls.observe(viewLifecycleOwner) { rolls ->
            adapter.submitList(rolls)
            binding.tvEmptyHistory.visibility = if (rolls.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerHistory.visibility = if (rolls.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.btnClearHistory.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm_clear_title)
                .setMessage(R.string.confirm_clear_message)
                .setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.deleteAllRolls()
                }
                .setNegativeButton(R.string.no, null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
