package com.lumiform.formtree.feature.contentitem.intent

/**
 * @created 19/07/2025 - 1:55 PM
 * @project FormTree
 * @author adell
 */
sealed class ContentItemIntent {
    object LoadContentItem : ContentItemIntent()
}