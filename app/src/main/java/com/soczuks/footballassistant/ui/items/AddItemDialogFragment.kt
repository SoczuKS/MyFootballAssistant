package com.soczuks.footballassistant.ui.items

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.database.entities.Item

class AddItemDialogFragment : DialogFragment() {
    interface AddItemDialogListener {
        fun onItemAdded(item: Item)
    }

    private var listener: AddItemDialogListener? = null

    private lateinit var itemNameEditText: TextInputEditText

    companion object {
        const val TAG = "AddItemDialog"
    }

    fun setListener(listener: AddItemDialogListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_item_dialog, null)

        itemNameEditText = view.findViewById(R.id.add_item_form_name)

        builder.setView(view).setPositiveButton(R.string.add, null)
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dismiss()
            }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (validateInput()) {
                    val itemName = itemNameEditText.text.toString().trim()
                    val item = Item(name = itemName)
                    listener?.onItemAdded(item)
                    dismiss()
                }
            }
        }

        return dialog
    }

    private fun validateInput(): Boolean {
        val itemName = itemNameEditText.text.toString().trim()

        return !itemName.isEmpty()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
