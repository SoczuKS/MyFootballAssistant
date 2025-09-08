package com.soczuks.footballassistant.ui.matches

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.databinding.FragmentMatchesBinding
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                matchesViewModel.viewModelMessage.collect { message ->
                    message?.let {
                        when (it.messageCode) {
                            ViewModelMessage.Code.Success -> {}
                            ViewModelMessage.Code.DeleteFailed -> {
                                Log.d("MatchesFragment", "Delete failed: ${it.message!!}")
                                Toast.makeText(
                                    requireContext(),
                                    R.string.delete_error,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        matchesViewModel.clearMessage()
                    }
                }
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
            val action = MatchesFragmentDirections.actionMatchesFragmentToMatchDetailsFragment(
                selectedMatch.match.id
            )
            findNavController().navigate(action)
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
