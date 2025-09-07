package com.soczuks.footballassistant.ui.competitions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soczuks.footballassistant.database.relations.CompetitionDetails
import com.soczuks.footballassistant.databinding.CompetitionsListBinding

class CompetitionAdapter(
    private val onItemClicked: (competition: CompetitionDetails) -> Unit,
    private val onItemLongClicked: (competition: CompetitionDetails) -> Unit
) :
    ListAdapter<CompetitionDetails, CompetitionAdapter.CompetitionViewHolder>(
        CompetitionDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        val binding =
            CompetitionsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompetitionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        val competition = getItem(position)
        holder.bind(competition)
        holder.itemView.setOnClickListener {
            onItemClicked(competition)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClicked(competition)
            true
        }
    }

    inner class CompetitionViewHolder(private val binding: CompetitionsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(competition: CompetitionDetails) {
            binding.competitionListName.text = competition.competition.name
        }
    }
}
