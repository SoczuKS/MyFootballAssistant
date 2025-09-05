package com.soczuks.footballassistant.ui.matches

import androidx.recyclerview.widget.DiffUtil
import com.soczuks.footballassistant.database.relations.MatchWithCompetitionAndItems

class MatchDiffCallback : DiffUtil.ItemCallback<MatchWithCompetitionAndItems>() {
    override fun areItemsTheSame(
        oldItem: MatchWithCompetitionAndItems, newItem: MatchWithCompetitionAndItems
    ): Boolean {
        return oldItem.match.id == newItem.match.id
    }

    override fun areContentsTheSame(
        oldItem: MatchWithCompetitionAndItems, newItem: MatchWithCompetitionAndItems
    ): Boolean {
        return oldItem == newItem
    }
}
