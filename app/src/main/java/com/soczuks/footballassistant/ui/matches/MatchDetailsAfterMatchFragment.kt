package com.soczuks.footballassistant.ui.matches

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.databinding.MatchDetailsAfterMatchBinding

class MatchDetailsAfterMatchFragment(private val match: MatchDetails) : Fragment() {
    private var _binding: MatchDetailsAfterMatchBinding? = null
    private var gradeListener: TextWatcher? = null
    private var homeScoreListener: TextWatcher? = null
    private var awayScoreListener: TextWatcher? = null
    private var noteListener: TextWatcher? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = MatchDetailsAfterMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        match.match.grade?.toString().let { binding.grade.setText(it) }
        match.match.awayScore?.toString().let { binding.awayScore.setText(it) }
        match.match.homeScore?.toString().let { binding.homeScore.setText(it) }
        match.match.note?.let { binding.notesTextInput.setText(it) }

        gradeListener = binding.grade.addTextChangedListener { editable ->
            // TODO: update match data
        }

        homeScoreListener = binding.homeScore.addTextChangedListener { editable ->
            // TODO: update match data
        }

        awayScoreListener = binding.awayScore.addTextChangedListener { editable ->
            // TODO: update match data
        }

        noteListener = binding.notesTextInput.addTextChangedListener { editable ->
            // TODO: update match data
        }
    }

    override fun onDestroyView() {
        gradeListener?.let { binding.grade.removeTextChangedListener(it) }
        homeScoreListener?.let { binding.homeScore.removeTextChangedListener(it) }
        awayScoreListener?.let { binding.awayScore.removeTextChangedListener(it) }
        noteListener?.let { binding.notesTextInput.removeTextChangedListener(it) }
        _binding = null
        super.onDestroyView()
    }
}