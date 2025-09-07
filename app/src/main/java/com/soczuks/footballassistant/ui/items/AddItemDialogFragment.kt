package com.soczuks.footballassistant.ui.items

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.databinding.AddItemDialogBinding

class AddItemDialogFragment : DialogFragment() {
    interface AddItemDialogListener {
        fun onItemAdded(item: Item)
    }

    private var listener: AddItemDialogListener? = null
    private var _binding: AddItemDialogBinding? = null

    private val binding get() = _binding!!

    companion object {
        const val TAG = "AddItemDialog"
    }

    fun setListener(listener: AddItemDialogListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        _binding = AddItemDialogBinding.inflate(requireActivity().layoutInflater)

        builder.setView(binding.root).setPositiveButton(R.string.add, null)
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dismiss()
            }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (validateInput()) {
                    val itemName = binding.addItemFormName.text.toString().trim()
                    val item = Item(name = itemName)
                    listener?.onItemAdded(item)
                    dismiss()
                }
            }
        }

        return dialog
    }

    private fun validateInput(): Boolean {
        val itemName = binding.addItemFormName.text.toString().trim()

        return !itemName.isEmpty()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
