package com.soczuks.footballassistant.ui.items

import androidx.recyclerview.widget.DiffUtil
import com.soczuks.footballassistant.database.entities.Item

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}