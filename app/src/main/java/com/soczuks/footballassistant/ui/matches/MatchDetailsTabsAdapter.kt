package com.soczuks.footballassistant.ui.matches

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.soczuks.footballassistant.database.relations.MatchDetails

class MatchDetailsTabsAdapter(
    private val match: MatchDetails,
    parent: Fragment
) : FragmentStateAdapter(parent) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MatchDetailsBeforeMatchFragment(match)
            else -> MatchDetailsAfterMatchFragment(match)
        }
}