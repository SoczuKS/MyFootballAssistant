package com.soczuks.footballassistant.ui.matches

import androidx.recyclerview.widget.DiffUtil
import com.soczuks.footballassistant.database.relations.MatchDetails

class MatchDiffCallback : DiffUtil.ItemCallback<MatchDetails>() {
    override fun areItemsTheSame(
        oldItem: MatchDetails, newItem: MatchDetails
    ): Boolean {
        return oldItem.match.id == newItem.match.id
    }

    override fun areContentsTheSame(
        oldItem: MatchDetails, newItem: MatchDetails
    ): Boolean {
        return oldItem == newItem
    }
}
