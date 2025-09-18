package com.soczuks.footballassistant.ui.items

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.databinding.FragmentItemsBinding
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.launch

class ItemsFragment : Fragment() {
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemsViewModel: ItemsViewModel
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        itemsViewModel = ViewModelProvider(this)[ItemsViewModel::class.java]

        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        itemsViewModel.items.observe(viewLifecycleOwner) { items ->
            items?.let {
                itemAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                itemsViewModel.viewModelMessage.collect { message ->
                    message?.let {
                        when (it.messageCode) {
                            ViewModelMessage.Code.Success -> {}
                            ViewModelMessage.Code.DeleteFailed -> {
                                Log.d("ItemsFragment", "Delete failed: ${it.message!!}")
                                Toast.makeText(
                                    requireContext(),
                                    R.string.delete_error,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            ViewModelMessage.Code.InsertFailed -> {
                                Log.d("ItemsFragment", "Insert failed: ${it.message!!}")
                                Toast.makeText(
                                    requireContext(),
                                    R.string.insert_error,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        itemsViewModel.clearMessage()
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
        itemAdapter = ItemAdapter({ selectedItem ->
            // TODO: Item details view
        }, { selectedItem ->
            AlertDialog.Builder(requireContext())
                .setTitle(selectedItem.name)
                .setMessage(R.string.item_delete_dialog_question)
                .setPositiveButton(R.string.yes) { _, _ ->
                    itemsViewModel.delete(selectedItem)
                }
                .setNegativeButton(R.string.no, null)
                .show()
        })
        binding.recyclerViewItems.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
