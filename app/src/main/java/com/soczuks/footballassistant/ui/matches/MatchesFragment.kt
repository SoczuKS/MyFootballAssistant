package com.soczuks.footballassistant.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.soczuks.footballassistant.databinding.FragmentMatchesBinding

class MatchesFragment : Fragment() {
    private var _binding: FragmentMatchesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val matchesViewModel =
            ViewModelProvider(this)[MatchesViewModel::class.java]

        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.text
        matchesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}