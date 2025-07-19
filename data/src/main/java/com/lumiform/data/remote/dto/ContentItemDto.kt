package com.lumiform.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * @created 17/07/2025 - 12:00 AM
 * @project FormTree
 * @author adell
 */
data class ContentItemDto(
    @SerializedName("type")
    val type: String,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("src")
    val src: String? = null,

    @SerializedName("items")
    val items: List<ContentItemDto>? = null
)