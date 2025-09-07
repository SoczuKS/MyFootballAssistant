package com.soczuks.footballassistant.ui.competitions

import androidx.recyclerview.widget.DiffUtil
import com.soczuks.footballassistant.database.relations.CompetitionDetails

class CompetitionDiffCallback : DiffUtil.ItemCallback<CompetitionDetails>() {
    override fun areItemsTheSame(
        oldItem: CompetitionDetails, newItem: CompetitionDetails
    ): Boolean {
        return oldItem.competition.id == newItem.competition.id
    }

    override fun areContentsTheSame(
        oldItem: CompetitionDetails, newItem: CompetitionDetails
    ): Boolean {
        return oldItem == newItem
    }
}
