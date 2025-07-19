package com.lumiform.data.remote.dto

/**
 * @created 17/07/2025 - 12:00 AM
 * @project FormTree
 * @author adell
 */
data class ContentItemDto(
    val type: String,
    val title: String? = null,
    val src: String? = null,
    val items: List<ContentItemDto>? = null
)