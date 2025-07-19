package com.lumiform.formtree.feature.contentitem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumiform.domain.Resource
import com.lumiform.domain.usecase.GetContentUseCase
import com.lumiform.formtree.feature.contentitem.intent.ContentItemIntent
import com.lumiform.formtree.feature.contentitem.state.ContentItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @created 17/07/2025 - 1:49 AM
 * @project FormTree
 * @author adell
 */
@HiltViewModel
class ContentItemViewModel @Inject constructor(
    private val getContentUseCase: GetContentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ContentItemState())
    val state: StateFlow<ContentItemState> = _state.asStateFlow()

    fun handleIntent(contentItemIntent: ContentItemIntent) {
        when (contentItemIntent) {
            is ContentItemIntent.LoadContentItem -> {
                getContentItems()
            }
        }
    }

    private fun getContentItems() {
        viewModelScope.launch(Dispatchers.IO) {
            getContentUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { contentItemState ->
                            contentItemState.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update { contentItemState ->
                            contentItemState.copy(
                                isLoading = false,
                                contentItem = result.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { contentItemState ->
                            contentItemState.copy(
                                isLoading = false,
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
            }
        }
    }
}