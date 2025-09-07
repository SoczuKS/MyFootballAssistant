package com.soczuks.footballassistant.ui.competitions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.relations.CompetitionDetails
import com.soczuks.footballassistant.databinding.CompetitionDetailsItemsListBinding
import com.soczuks.footballassistant.ui.items.ItemDiffCallback

class CompetitionItemAssignmentAdapter(
    private val competition: CompetitionDetails,
    private val onItemCheckedChanged: (item: Item, isChecked: Boolean) -> Unit
) :
    ListAdapter<Item, CompetitionItemAssignmentAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CompetitionDetailsItemsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: CompetitionDetailsItemsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.itemNameTextView.text = item.name
            binding.itemCheckBox.isChecked = competition.items.any { it.id == item.id }
            binding.itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onItemCheckedChanged(item, isChecked)
            }
        }
    }
}
