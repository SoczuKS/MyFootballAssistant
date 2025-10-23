package com.soczuks.footballassistant.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.MatchDetailsBeforeMatchBinding

class MatchDetailsBeforeMatchFragment(private val match: MatchDetails) : Fragment() {
    private var _binding: MatchDetailsBeforeMatchBinding? = null

    private val binding get() = _binding!!

    private lateinit var matchItemCheckedAdapter: MatchItemCheckedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MatchDetailsBeforeMatchBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        matchItemCheckedAdapter = MatchItemCheckedAdapter(match) { item, isChecked ->
            // Handle item checked state change if needed
        }
        binding.matchDetailsItems.apply {
            adapter = matchItemCheckedAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        }
        matchItemCheckedAdapter.submitList(match.items)
    }
}