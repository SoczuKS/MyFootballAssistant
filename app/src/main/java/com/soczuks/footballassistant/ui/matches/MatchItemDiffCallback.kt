package com.soczuks.footballassistant.ui.matches

import androidx.recyclerview.widget.DiffUtil
import com.soczuks.footballassistant.database.entities.MatchItem

class MatchItemDiffCallback : DiffUtil.ItemCallback<MatchItem>() {
    override fun areItemsTheSame(oldMatchItem: MatchItem, newMatchItem: MatchItem): Boolean {
        return oldMatchItem.itemId == newMatchItem.itemId &&
               oldMatchItem.matchId == newMatchItem.matchId
    }

    override fun areContentsTheSame(oldMatchItem: MatchItem, newMatchItem: MatchItem): Boolean {
        return oldMatchItem == newMatchItem
    }
}
