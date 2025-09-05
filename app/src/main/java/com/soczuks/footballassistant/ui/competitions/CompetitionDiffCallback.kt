package com.soczuks.footballassistant.ui.competitions

import androidx.recyclerview.widget.DiffUtil
import com.soczuks.footballassistant.database.relations.CompetitionWithItems

class CompetitionDiffCallback : DiffUtil.ItemCallback<CompetitionWithItems>() {
    override fun areItemsTheSame(oldItem: CompetitionWithItems, newItem: CompetitionWithItems): Boolean {
        return oldItem.competition.id == newItem.competition.id
    }

    override fun areContentsTheSame(oldItem: CompetitionWithItems, newItem: CompetitionWithItems): Boolean {
        return oldItem == newItem
    }
}