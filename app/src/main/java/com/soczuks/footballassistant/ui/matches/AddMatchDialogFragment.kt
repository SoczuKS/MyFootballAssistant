package com.soczuks.footballassistant.ui.matches

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.database.entities.Match
import com.soczuks.footballassistant.databinding.AddMatchDialogBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class AddMatchDialogFragment : DialogFragment() {
    interface AddMatchDialogListener {
        fun onMatchAdded(match: Match)
    }

    private var listener: AddMatchDialogListener? = null
    private var selectedDateTime: Calendar? = null
    private var _binding: AddMatchDialogBinding? = null

    private val binding get() = _binding!!

    companion object {
        const val TAG = "AddMatchDialog"
    }

    fun setListener(listener: AddMatchDialogListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        _binding = AddMatchDialogBinding.inflate(requireActivity().layoutInflater)

        binding.addMatchFormDate.setOnClickListener { showDatePicker() }

        builder.setView(binding.root).setPositiveButton(R.string.add, null)
            .setNegativeButton(R.string.cancel) { dialog, id -> dismiss() }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (validateInput()) {
                    val rivalName = binding.addMatchFormRivalTeam.text.toString()
                    val newMatch = Match(
                        rivalTeam = rivalName,
                        competitionId = 1,
                        isHome = binding.addMatchFormIsHomeGame.isChecked,
                        matchDate = selectedDateTime!!.time
                    )
                    listener?.onMatchAdded(newMatch)
                    dialog.dismiss()
                }
            }
        }

        return dialog
    }

    private fun validateInput(): Boolean {
        val rivalName = binding.addMatchFormRivalTeam.text.toString().trim()

        return !rivalName.isEmpty() && selectedDateTime != null
    }

    private fun showTimePicker() {
        val currentHour: Int
        val currentMinute: Int

        if (selectedDateTime != null) {
            val localCalendar = Calendar.getInstance()
            localCalendar.timeInMillis = selectedDateTime!!.timeInMillis
            currentHour = localCalendar.get(Calendar.HOUR_OF_DAY)
            currentMinute = localCalendar.get(Calendar.MINUTE)
        } else {
            val now = Calendar.getInstance()
            currentHour = now.get(Calendar.HOUR_OF_DAY)
            currentMinute = now.get(Calendar.MINUTE)
        }

        val timePicker =
            MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(currentHour)
                .setMinute(currentMinute).setTitleText(R.string.time_picker_title)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK).build()

        timePicker.addOnPositiveButtonClickListener {
            selectedDateTime!!.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            selectedDateTime!!.set(Calendar.MINUTE, timePicker.minute)
            selectedDateTime!!.set(Calendar.SECOND, 0)
            selectedDateTime!!.set(Calendar.MILLISECOND, 0)

            updateDateTimeEditText()
        }
        timePicker.show(parentFragmentManager, "MATCH_TIME_PICKER_TAG")
    }

    private fun showDatePicker() {
        val currentSelection =
            selectedDateTime?.timeInMillis ?: MaterialDatePicker.todayInUtcMilliseconds()

        val datePickerBuilder =
            MaterialDatePicker.Builder.datePicker().setTitleText(R.string.date_picker_title)
                .setSelection(currentSelection)

        val datePicker = datePickerBuilder.build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val tempCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            tempCalendar.timeInMillis = selection

            if (selectedDateTime == null) {
                selectedDateTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            }

            selectedDateTime!!.set(
                tempCalendar.get(Calendar.YEAR),
                tempCalendar.get(Calendar.MONTH),
                tempCalendar.get(Calendar.DAY_OF_MONTH)
            )
            showTimePicker()
        }
        datePicker.show(parentFragmentManager, "MATCH_DATE_PICKER_TAG")
    }

    private fun updateDateTimeEditText() {
        if (selectedDateTime != null) {
            val displayFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            displayFormat.timeZone = TimeZone.getDefault()
            binding.addMatchFormDate.setText(displayFormat.format(selectedDateTime!!.time))
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
