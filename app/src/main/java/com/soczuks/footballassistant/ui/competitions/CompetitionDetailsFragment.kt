package com.soczuks.footballassistant.ui.competitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.soczuks.footballassistant.database.relations.CompetitionDetails
import com.soczuks.footballassistant.databinding.FragmentCompetitionDetailsBinding
import com.soczuks.footballassistant.ui.items.ItemsViewModel

class CompetitionDetailsFragment : Fragment() {
    private var _binding: FragmentCompetitionDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var itemsViewModel: ItemsViewModel
    private lateinit var competitionViewModel: CompetitionsViewModel

    private val args: CompetitionDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemsViewModel = ViewModelProvider(this)[ItemsViewModel::class.java]
        competitionViewModel = ViewModelProvider(this)[CompetitionsViewModel::class.java]
        _binding = FragmentCompetitionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        competitionViewModel.getCompetitionById(args.competitionId)
            ?.observe(viewLifecycleOwner) { competition ->
                if (competition != null) {
                    setupUI(competition)
                }
            }
    }

    private fun setupUI(competition: CompetitionDetails) {
        binding.competitionDetailsName.text = competition.competition.name
        itemsViewModel.items.observe(viewLifecycleOwner) { items ->
            val adapter = CompetitionItemAssignmentAdapter(competition) { item, isChecked ->
                if (isChecked) {
                    competitionViewModel.assignItemToCompetition(competition, item)
                } else {
                    competitionViewModel.unassignItemFromCompetition(competition, item)
                }
            }

            binding.competitionDetailsItems.adapter = adapter
            adapter.submitList(items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
