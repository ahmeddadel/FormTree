package com.lumiform.formtree.feature.contentitem.screen

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lumiform.domain.model.ContentItemModel
import com.lumiform.formtree.R
import com.lumiform.formtree.databinding.FragmentContentItemBinding
import com.lumiform.formtree.feature.contentitem.adapter.ContentItemAdapter
import com.lumiform.formtree.feature.contentitem.adapter.DisplayItem
import com.lumiform.formtree.feature.contentitem.intent.ContentItemIntent
import com.lumiform.formtree.feature.contentitem.viewmodel.ContentItemViewModel
import com.lumiform.formtree.utils.getSpotsDialogProgress
import com.lumiform.formtree.utils.showCustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentItemFragment : Fragment() {

    private lateinit var binding: FragmentContentItemBinding
    private lateinit var spotsDialogProgress: AlertDialog // Progress dialog
    private lateinit var adapter: ContentItemAdapter
    private val viewModel: ContentItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentItemBinding.inflate(inflater, container, false)

        spotsDialogProgress = requireActivity().getSpotsDialogProgress()

        adapter = ContentItemAdapter { imageUrl, title ->
            findNavController().navigate(
                ContentItemFragmentDirections.actionContentItemFragmentToContentItemImageFragment(
                    imageUrl,
                    title
                )
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect { result ->
                if (result.isLoading) {
                    if (!spotsDialogProgress.isShowing) {
                        spotsDialogProgress.show()
                    }
                } else {
                    if (spotsDialogProgress.isShowing) {
                        spotsDialogProgress.dismiss()
                    }
                }

                if (result.contentItem.isNotEmpty()) {
                    adapter.submitList(flattenContent(result.contentItem))
                }

                if (!result.error.isNullOrEmpty()) {
                    requireActivity().showCustomAlertDialog(
                        title = getString(R.string.oops),
                        message = result.error,
                        showCancelButton = true,
                        okButtonText = getString(R.string.retry),
                        cancelButtonText = getString(R.string.close),
                        onOkClick = {
                            viewModel.handleIntent(ContentItemIntent.LoadContentItem) // Retry fetching content items
                        },
                        onCancelClick = {
                            requireActivity().finish()
                        }
                    )
                }
            }
        }

        viewModel.handleIntent(ContentItemIntent.LoadContentItem)
    }

    private fun flattenContent(items: List<ContentItemModel>, depth: Int = 0): List<DisplayItem> {
        val result = mutableListOf<DisplayItem>()
        for (item in items) {
            result.add(DisplayItem(item, depth))
            when (item) {
                is ContentItemModel.Page -> result.addAll(flattenContent(item.items, depth + 1))
                is ContentItemModel.Section -> result.addAll(flattenContent(item.items, depth + 1))
                else -> {} // Questions have no children
            }
        }
        return result
    }
}