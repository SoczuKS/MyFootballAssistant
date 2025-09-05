package com.soczuks.footballassistant.ui.matches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.database.relations.MatchWithCompetitionAndItems

class MatchAdapter(private val onItemClicked: (match: MatchWithCompetitionAndItems) -> Unit) :
    ListAdapter<MatchWithCompetitionAndItems, MatchAdapter.MatchViewHolder>(MatchDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.matches_list, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = getItem(position)
        holder.bind(match)
        holder.itemView.setOnClickListener {
            onItemClicked(match)
        }
    }

    class MatchViewHolder(matchView: View) : RecyclerView.ViewHolder(matchView) {
        private val dateTextView: TextView = matchView.findViewById(R.id.matches_list_date)
        private val rivalTeamTextView: TextView =
            matchView.findViewById(R.id.matches_list_rival_team)

        fun bind(match: MatchWithCompetitionAndItems) {
            val date =
                java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale.getDefault())
                    .format(match.match.matchDate)
            dateTextView.text = date
            rivalTeamTextView.text = match.match.rivalTeam
        }
    }
}
