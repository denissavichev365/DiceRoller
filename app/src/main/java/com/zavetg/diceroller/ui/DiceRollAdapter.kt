package com.zavetg.diceroller.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zavetg.diceroller.R
import com.zavetg.diceroller.data.DiceRoll
import com.zavetg.diceroller.databinding.ItemDiceRollBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiceRollAdapter(
    private val onItemClick: (DiceRoll) -> Unit
) : ListAdapter<DiceRoll, DiceRollAdapter.DiceRollViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceRollViewHolder {
        val binding = ItemDiceRollBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DiceRollViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiceRollViewHolder, position: Int) {
        val roll = getItem(position)
        holder.bind(roll)
        holder.itemView.setOnClickListener { onItemClick(roll) }
    }

    class DiceRollViewHolder(
        private val binding: ItemDiceRollBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        fun bind(roll: DiceRoll) {
            binding.tvDiceType.text = binding.root.context.getString(
                R.string.dice_type_label, roll.diceCount, roll.diceType
            )
            binding.tvRollResults.text = roll.results.replace(",", ", ")
            binding.tvRollDate.text = dateFormat.format(Date(roll.createdAt))
            binding.tvRollSum.text = roll.sum.toString()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DiceRoll>() {
        override fun areItemsTheSame(oldItem: DiceRoll, newItem: DiceRoll): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DiceRoll, newItem: DiceRoll): Boolean {
            return oldItem == newItem
        }
    }
}
