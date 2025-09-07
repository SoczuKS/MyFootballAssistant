package com.soczuks.footballassistant.ui.matches

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.databinding.FragmentMatchesBinding

class MatchesFragment : Fragment() {
    private var _binding: FragmentMatchesBinding? = null

    private val binding get() = _binding!!

    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var matchAdapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        matchAdapter = MatchAdapter({ selectedMatch ->
            Log.d(
                "MatchesFragment", "Selected match: ${selectedMatch.match.rivalTeam}"
            )
        }, { selectedMatch ->
            AlertDialog.Builder(requireContext())
                .setTitle(selectedMatch.match.rivalTeam)
                .setMessage(R.string.match_delete_dialog_question)
                .setPositiveButton(R.string.yes) { _, _ ->
                    matchesViewModel.delete(selectedMatch)
                }
                .setNegativeButton(R.string.no, null)
                .show()
        })

        binding.recyclerViewMatches.apply {
            adapter = matchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
