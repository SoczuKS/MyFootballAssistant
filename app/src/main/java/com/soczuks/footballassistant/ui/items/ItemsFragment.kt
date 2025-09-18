package com.soczuks.footballassistant.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soczuks.footballassistant.R
import com.soczuks.footballassistant.databinding.FragmentItemsBinding

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
