package com.lumiform.domain.model

/**
 * @created 17/07/2025 - 12:18 AM
 * @project FormTree
 * @author adell
 */
sealed class ContentItemModel {
    data class Page(val title: String, val items: List<ContentItemModel>) : ContentItemModel()
    data class Section(val title: String, val items: List<ContentItemModel>) : ContentItemModel()
    data class TextQuestion(val title: String) : ContentItemModel()
    data class ImageQuestion(val src: String, val title: String) : ContentItemModel()
}