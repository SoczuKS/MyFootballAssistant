package com.soczuks.footballassistant.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.FragmentMatchDetailsBinding
import com.soczuks.footballassistant.ui.competitions.CompetitionsViewModel
import com.soczuks.footballassistant.ui.items.ItemsViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MatchDetailsFragment : Fragment() {
    private var _binding: FragmentMatchDetailsBinding? = null
    private var previousTabPosition = 0

    private val binding get() = _binding!!
    private val args: MatchDetailsFragmentArgs by navArgs()

    private lateinit var matchViewModel: MatchesViewModel
    private lateinit var itemsViewModel: ItemsViewModel
    private lateinit var competitionsViewModel: CompetitionsViewModel

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI(match: MatchDetails) {
        binding.matchDetailsCompetition.text = match.competition.name
        binding.matchDetailsRival.text = match.match.rivalTeam
        val displayFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        displayFormat.timeZone = TimeZone.getDefault()
        binding.matchDetailsDate.text = displayFormat.format(match.match.matchDate.time)
        binding.matchDetailsViewPager.adapter = MatchDetailsTabsAdapter(match, this)
        TabLayoutMediator(
            binding.matchDetailsTabLayout,
            binding.matchDetailsViewPager
        ) { tab, position ->
            tab.text = getString(if (position == 0) R.string.before_match else R.string.after_match)
        }.attach()

        val shouldDisableAfterMatchTab =
            match.match.matchDate.time > System.currentTimeMillis()
        if (shouldDisableAfterMatchTab) {
            disableAfterMatchDetailsTab()
        }
        setupTabSelectionGuard()
    }

    private fun disableAfterMatchDetailsTab() {
        val tabStrip = binding.matchDetailsTabLayout.getChildAt(0) as ViewGroup
        val tabView = tabStrip.getChildAt(1)
        tabView.isEnabled = false
        tabView.alpha = 0.35f
    }

    private fun setupTabSelectionGuard() {
        binding.matchDetailsTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabStrip = binding.matchDetailsTabLayout.getChildAt(0) as ViewGroup
                val tabView = tabStrip.getChildAt(tab.position)
                if (!tabView.isEnabled) {
                    binding.matchDetailsViewPager.currentItem = previousTabPosition
                } else {
                    previousTabPosition = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }
}
