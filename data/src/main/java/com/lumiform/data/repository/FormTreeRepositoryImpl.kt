package com.lumiform.data.repository

import android.util.Log
import com.google.gson.Gson
import com.lumiform.data.local.db.dao.CachedJsonDao
import com.lumiform.data.local.db.entity.CachedJsonEntity
import com.lumiform.data.local.mapper.toDomain
import com.lumiform.data.remote.api.IApiService
import com.lumiform.data.remote.dto.ContentItemDto
import com.lumiform.domain.model.ContentItemModel
import com.lumiform.domain.repository.IFormTreeRepository
import java.io.IOException

/**
 * @created 17/07/2025 - 12:04 AM
 * @project FormTree
 * @author adell
 */
class FormTreeRepositoryImpl(
    private val contentApiService: IApiService,
    private val dao: CachedJsonDao,
    private val gson: Gson
) : IFormTreeRepository {

    companion object {
        private const val TAG = "MainRepositoryImpl"
    }

    override suspend fun getContent(): List<ContentItemModel> {
        return try {
            // Try remote fetch
            val contentItemDto: ContentItemDto = contentApiService.fetchContent()
            val json = gson.toJson(contentItemDto)

            if (dao.getAllCached().isNotEmpty()) {
                if (dao.clearCache() > 0) {
                    dao.insertCache(CachedJsonEntity(json = json))
                } else {
                    Log.e(TAG, "Failed to clear cache")
                }
            } else {
                dao.insertCache(CachedJsonEntity(json = json))
            }

            contentItemDto.toDomain()?.let { listOf(it) }
                ?: throw IllegalStateException("Parsed domain content is null")
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching content from network: ${e.message}", e)

            val cachedJson = dao.getLastCached()?.json
                ?: throw IOException("No cached data available", e)

            val cachedDto = gson.fromJson(cachedJson, ContentItemDto::class.java)
            cachedDto.toDomain()?.let { listOf(it) }
                ?: throw IOException("Cached content is corrupted or invalid", e)
        }
    }
}