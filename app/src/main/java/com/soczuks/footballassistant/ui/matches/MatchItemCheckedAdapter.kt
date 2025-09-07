package com.soczuks.footballassistant.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.MatchDetailsItemsListBinding
import com.soczuks.footballassistant.ui.items.ItemDiffCallback

class MatchItemCheckedAdapter(
    private val match: MatchDetails,
    private val onItemCheckedChanged: (item: Item, isChecked: Boolean) -> Unit
) : ListAdapter<Item, MatchItemCheckedAdapter.ViewHolder>(
    ItemDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MatchDetailsItemsListBinding.inflate(
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

    inner class ViewHolder(private val binding: MatchDetailsItemsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.itemNameTextView.text = item.name

            binding.itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onItemCheckedChanged(item, isChecked)
            }
        }
    }
}