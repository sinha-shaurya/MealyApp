package com.example.mealy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mealy.R
import com.example.mealy.databinding.DayItemBinding

class DayAdapter(val listener: onItemClickListener, val context: Context) :
    ListAdapter<String, DayAdapter.DayViewHolder>(DayDiffUtilCallback()) {
    inner class DayViewHolder(private val binding: DayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: String) {
            binding.dayName.text = day
            val color = assignColors(day)
            println(color)
            binding.rootLayout.setBackgroundColor(ContextCompat.getColor(context, color))
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null)
                        listener.onItemClick(item)
                }
            }
        }

        fun assignColors(day: String): Int {
            return when (day) {
                "Monday" -> R.color.monday
                "Tuesday" -> R.color.tuesday
                "Wednesday" -> R.color.wednesday
                "Thursday" -> R.color.thursday
                "Friday" -> R.color.friday
                "Saturday" -> R.color.saturday
                else -> R.color.sunday
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = DayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface onItemClickListener {
        fun onItemClick(day: String)
    }

}

class DayDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

}