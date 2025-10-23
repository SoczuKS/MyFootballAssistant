package com.soczuks.footballassistant.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soczuks.footballassistant.database.entities.MatchItem
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.MatchDetailsItemsListBinding
import com.soczuks.footballassistant.ui.items.ItemDiffCallback

class MatchItemCheckedAdapter(
    private val match: MatchDetails,
    private val onItemCheckedChanged: (item: MatchItem, isChecked: Boolean) -> Unit
) : ListAdapter<MatchItem, MatchItemCheckedAdapter.ViewHolder>(
    MatchItemDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MatchDetailsItemsListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: MatchDetailsItemsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchItem) {
            binding.itemNameTextView.text = item.itemId.toString()
            binding.itemCheckBox.isChecked = item.checked
            binding.itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onItemCheckedChanged(item, isChecked)
            }
        }
    }
}