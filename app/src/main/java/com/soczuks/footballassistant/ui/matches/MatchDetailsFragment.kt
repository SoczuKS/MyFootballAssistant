package com.soczuks.footballassistant.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.FragmentMatchDetailsBinding
import com.soczuks.footballassistant.ui.competitions.CompetitionsViewModel
import com.soczuks.footballassistant.ui.items.ItemsViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MatchDetailsFragment : Fragment() {
    private var _binding: FragmentMatchDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var matchViewModel: MatchesViewModel
    private lateinit var itemsViewModel: ItemsViewModel
    private lateinit var competitionsViewModel: CompetitionsViewModel

    private val args: MatchDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemsViewModel = ViewModelProvider(this)[ItemsViewModel::class.java]
        matchViewModel = ViewModelProvider(this)[MatchesViewModel::class.java]
        competitionsViewModel = ViewModelProvider(this)[CompetitionsViewModel::class.java]
        _binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchViewModel.getMatchById(args.matchId)?.observe(viewLifecycleOwner) { match ->
            if (match != null) {
                setupUI(match)
            }
        }
    }

    private fun setupUI(match: MatchDetails) {
        binding.matchDetailsRival.text = match.match.rivalTeam
        val displayFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        displayFormat.timeZone = TimeZone.getDefault()
        binding.matchDetailsDate.text = displayFormat.format(match.match.matchDate.time)
        // TODO: More info
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
