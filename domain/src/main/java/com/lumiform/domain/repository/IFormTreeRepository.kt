package com.lumiform.domain.repository

import com.lumiform.domain.model.ContentItemModel

/**
 * @created 17/07/2025 - 12:19 AM
 * @project FormTree
 * @author adell
 */
interface IFormTreeRepository {

    /**
     * Fetches the content items.
     *
     * @return a list of content items [ContentItemModel].
     */
    suspend fun getContent(): List<ContentItemModel>
}