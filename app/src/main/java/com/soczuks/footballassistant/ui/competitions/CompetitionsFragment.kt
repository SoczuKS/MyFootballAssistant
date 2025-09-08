package com.soczuks.footballassistant.ui.competitions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.databinding.FragmentCompetitionsBinding
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                competitionsViewModel.viewModelMessage.collect { message ->
                    message?.let {
                        when (it.messageCode) {
                            ViewModelMessage.Code.Success -> {}
                            ViewModelMessage.Code.DeleteFailed -> {
                                Log.d("CompetitionsFragment", "Delete failed: ${it.message!!}")
                                Toast.makeText(
                                    requireContext(),
                                    R.string.delete_error,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        competitionsViewModel.clearMessage()
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
        competitionAdapter = CompetitionAdapter({ selectedCompetition ->
            val action =
                CompetitionsFragmentDirections.actionCompetitionsFragmentToCompetitionDetailsFragment(
                    selectedCompetition.competition.id
                )
            findNavController().navigate(action)
        }, { selectedCompetition ->
            AlertDialog.Builder(requireContext())
                .setTitle(selectedCompetition.competition.name)
                .setMessage(R.string.competition_delete_dialog_question)
                .setPositiveButton(R.string.yes) { _, _ ->
                    competitionsViewModel.delete(selectedCompetition)
                }
                .setNegativeButton(R.string.no, null)
                .show()
        })
        binding.recyclerViewCompetitions.apply {
            adapter = competitionAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        }
    }
}
