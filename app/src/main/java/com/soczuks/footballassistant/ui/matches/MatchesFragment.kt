package com.soczuks.footballassistant.ui.matches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soczuks.footballassistant.databinding.FragmentMatchesBinding

class MatchesFragment : Fragment() {
    private var _binding: FragmentMatchesBinding? = null

    private val binding get() = _binding!!

    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var matchAdapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        matchesViewModel = ViewModelProvider(this)[MatchesViewModel::class.java]

        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        matchesViewModel.matches.observe(viewLifecycleOwner) { matches ->
            matches?.let {
                matchAdapter.submitList(it)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        matchAdapter = MatchAdapter { selectedItem ->
            Log.d(
                "MatchesFragment",
                "Selected match: ${selectedItem.match.rivalTeam}"
            )
        }

        binding.recyclerViewMatches.apply {
            adapter = matchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}