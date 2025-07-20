package com.lumiform.domain.usecase

import android.content.Context
import com.lumiform.domain.R
import com.lumiform.domain.Resource
import com.lumiform.domain.model.ContentItemModel
import com.lumiform.domain.repository.IFormTreeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @created 17/07/2025 - 12:20 AM
 * @project FormTree
 * @author adell
 */
class GetContentUseCase @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val formTreeRepository: IFormTreeRepository
) {

    /**
     * Fetches the content items.
     *
     * @return a list of content items [ContentItemModel].
     */
    operator fun invoke(): Flow<Resource<List<ContentItemModel>>> = flow {
        try {
            emit(Resource.Loading())
            val contentItems = formTreeRepository.getContent()
            emit(Resource.Success(contentItems))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: context.getString(R.string.error_generic)))
        }
    }

}