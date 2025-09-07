package com.soczuks.footballassistant.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.MatchesListBinding

class MatchAdapter(
    private val onItemClicked: (match: MatchDetails) -> Unit,
    private val onItemLongClicked: (match: MatchDetails) -> Unit
) :
    ListAdapter<MatchDetails, MatchAdapter.MatchViewHolder>(MatchDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = MatchesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = getItem(position)
        holder.bind(match)
        holder.itemView.setOnClickListener {
            onItemClicked(match)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClicked(match)
            true
        }
    }

    inner class MatchViewHolder(private val binding: MatchesListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(match: MatchDetails) {
            binding.matchesListDate.text =
                java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale.getDefault())
                    .format(match.match.matchDate)
            binding.matchesListRivalTeam.text = match.match.rivalTeam
        }
    }
}
