package com.soczuks.footballassistant.ui.competitions

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.databinding.AddCompetitionDialogBinding
import kotlinx.coroutines.launch

class AddCompetitionDialogFragment : DialogFragment() {
    private var _binding: AddCompetitionDialogBinding? = null

    private lateinit var competitionsViewModel: CompetitionsViewModel

    private val binding get() = _binding!!

    companion object {
        const val TAG = "AddCompetitionDialog"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        _binding = AddCompetitionDialogBinding.inflate(requireActivity().layoutInflater)
        competitionsViewModel = ViewModelProvider(this)[CompetitionsViewModel::class.java]

        builder.setView(binding.root).setPositiveButton(R.string.add, null)
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dismiss()
            }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (validateInput()) {
                    val competitionName = binding.addCompetitionFormName.text.toString().trim()
                    val teamName = binding.addCompetitionFormTeam.text.toString().trim()
                    val competition = Competition(name = competitionName, team = teamName)

                    lifecycleScope.launch {
                        val newCompetitionId = competitionsViewModel.insert(competition)

                        if (newCompetitionId != null) {
                            dismiss()
                        }
                    }
                }
            }
        }

        return dialog
    }

    private fun validateInput(): Boolean {
        val competitionName = binding.addCompetitionFormName.text.toString().trim()
        val teamName = binding.addCompetitionFormTeam.text.toString().trim()

        return !competitionName.isEmpty() && !teamName.isEmpty()
    }
}
