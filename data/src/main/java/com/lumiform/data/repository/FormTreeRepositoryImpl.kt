package com.lumiform.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.lumiform.data.R
import com.lumiform.data.local.db.dao.CachedJsonDao
import com.lumiform.data.local.db.entity.CachedJsonEntity
import com.lumiform.data.local.mapper.toDomain
import com.lumiform.data.remote.api.IApiService
import com.lumiform.data.remote.dto.ContentItemDto
import com.lumiform.domain.model.ContentItemModel
import com.lumiform.domain.repository.IFormTreeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

/**
 * @created 17/07/2025 - 12:04 AM
 * @project FormTree
 * @author adell
 */
class FormTreeRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val contentApiService: IApiService,
    private val dao: CachedJsonDao,
    private val gson: Gson
) : IFormTreeRepository {

    companion object {
        private const val TAG = "FormTreeRepositoryImpl"
    }

    override suspend fun getContent(): List<ContentItemModel> {
        return try {
            // Try remote fetch
            val contentItemDto: ContentItemDto = contentApiService.fetchContent()

            val json = gson.toJson(contentItemDto)
            val isCacheNotEmpty = dao.getAllCached().isNotEmpty()
            if (isCacheNotEmpty) {
                if (dao.clearCache() > 0) {
                    Log.d(TAG, "Cache cleared successfully")
                } else {
                    Log.e(TAG, "Failed to clear cache")
                }
            }
            dao.insertCache(CachedJsonEntity(json = json))

            contentItemDto.toDomain()?.let { listOf(it) }
                ?: throw IllegalStateException(context.getString(R.string.error_parsed_content_null))
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching content from network: ${e.message}", e)

            val cachedJson = dao.getLastCached()?.json
                ?: throw IOException(context.getString(R.string.error_no_cache), e)

            val cachedDto = gson.fromJson(cachedJson, ContentItemDto::class.java)
            cachedDto.toDomain()?.let { listOf(it) }
                ?: throw IOException(context.getString(R.string.error_cache_corrupted), e)
        }
    }
}