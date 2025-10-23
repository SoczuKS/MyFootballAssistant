package com.soczuks.footballassistant.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.MatchDetailsAfterMatchBinding

class MatchDetailsAfterMatchFragment(private val match: MatchDetails) : Fragment() {
    private var _binding: MatchDetailsAfterMatchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MatchDetailsAfterMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}