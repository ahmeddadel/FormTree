package com.lumiform.domain.usecase

import com.lumiform.domain.Resource
import com.lumiform.domain.model.ContentItemModel
import com.lumiform.domain.repository.IFormTreeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @created 17/07/2025 - 12:20 AM
 * @project FormTree
 * @author adell
 */
class GetContentUseCase(private val mainRepository: IFormTreeRepository) {

    /**
     * Fetches the content items.
     *
     * @return a list of content items [ContentItemModel].
     */
    operator fun invoke(): Flow<Resource<List<ContentItemModel>>> = flow {
        try {
            emit(Resource.Loading())
            val contentItems = mainRepository.getContent()
            emit(Resource.Success(contentItems))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}