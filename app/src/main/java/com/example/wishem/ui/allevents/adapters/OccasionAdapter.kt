package com.example.wishem.ui.allevents.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wishem.databinding.HolderAllEventsBinding
import com.example.wishem.databinding.HolderTodayOccasionsBinding
import com.example.wishem.local.OccasionEntity

class OccasionAdapter(
        private val clickItem : (item: OccasionEntity, itemView: View) -> Unit,
        private val isToday: Boolean = true
) : ListAdapter<OccasionEntity, RecyclerView.ViewHolder>(diffUtil) {


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OccasionEntity>() {

            override fun areItemsTheSame(oldItem: OccasionEntity, newItem: OccasionEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: OccasionEntity, newItem: OccasionEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(isToday) {
            val binding = HolderTodayOccasionsBinding.inflate(inflater)
            TodaysOccasionViewHolder(binding)
        }else {
            val binding = HolderAllEventsBinding.inflate(inflater)
            AllOccasionViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val occasion = getItem(position)
        when(holder) {
            is TodaysOccasionViewHolder -> holder.setupHolder(occasion)
            is AllOccasionViewHolder -> holder.setupHolder(occasion)
        }
    }

    inner class TodaysOccasionViewHolder(val binding : HolderTodayOccasionsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setupHolder(item : OccasionEntity) {
            binding.item = item

            binding.cvTodayOccasion.setOnClickListener {
                clickItem(item, it)
            }
        }
    }

    inner class AllOccasionViewHolder(val binding : HolderAllEventsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setupHolder(item : OccasionEntity) {
            binding.item = item

            binding.llEvent.setOnClickListener {
                clickItem(item, it)
            }
        }
    }
}
