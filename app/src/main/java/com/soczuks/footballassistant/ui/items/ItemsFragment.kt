package com.soczuks.footballassistant.ui.items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        itemAdapter = ItemAdapter { selectedItem ->
            Log.d("ItemsFragment", "Selected item: ${selectedItem.name}")
        }
        binding.recyclerViewItems.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
