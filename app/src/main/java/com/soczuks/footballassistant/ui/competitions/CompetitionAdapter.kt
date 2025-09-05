package com.soczuks.footballassistant.ui.competitions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.database.relations.CompetitionWithItems

class CompetitionAdapter(private val onItemClicked: (competition: CompetitionWithItems) -> Unit) :
    ListAdapter<CompetitionWithItems, CompetitionAdapter.CompetitionViewHolder>(
        CompetitionDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.competitions_list, parent, false)
        return CompetitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        val competition = getItem(position)
        holder.bind(competition)
        holder.itemView.setOnClickListener {
            onItemClicked(competition)
        }
    }

    class CompetitionViewHolder(competitionView: View) : RecyclerView.ViewHolder(competitionView) {
        private val nameTextView: TextView =
            competitionView.findViewById(R.id.competition_list_name)

        fun bind(competition: CompetitionWithItems) {
            nameTextView.text = competition.competition.name
        }
    }
}
