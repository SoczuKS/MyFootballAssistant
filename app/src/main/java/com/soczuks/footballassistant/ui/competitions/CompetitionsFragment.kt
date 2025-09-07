package com.soczuks.footballassistant.ui.competitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.soczuks.footballassistant.databinding.FragmentCompetitionsBinding

class CompetitionsFragment : Fragment() {
    private var _binding: FragmentCompetitionsBinding? = null

    private val binding get() = _binding!!

    private lateinit var competitionsViewModel: CompetitionsViewModel
    private lateinit var competitionAdapter: CompetitionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        competitionsViewModel = ViewModelProvider(this)[CompetitionsViewModel::class.java]

        _binding = FragmentCompetitionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        competitionsViewModel.competitions.observe(viewLifecycleOwner) { competitions ->
            competitions?.let {
                competitionAdapter.submitList(it)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        competitionAdapter = CompetitionAdapter { selectedItem ->
            val action =
                CompetitionsFragmentDirections.actionCompetitionsFragmentToCompetitionDetailsFragment(
                    selectedItem.competition.id
                )
            findNavController().navigate(action)
        }
        binding.recyclerViewCompetitions.apply {
            adapter = competitionAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        }
    }
}
