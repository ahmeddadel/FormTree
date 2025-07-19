package com.lumiform.formtree.feature.contentitem.state

import com.lumiform.domain.model.ContentItemModel

/**
 * @created 18/07/2025 - 6:45 PM
 * @project FormTree
 * @author adell
 */
data class ContentItemState(
    val isLoading: Boolean = false,
    val contentItem: List<ContentItemModel> = emptyList(),
    val error: String? = null
)