package com.lumiform.formtree.di

import com.lumiform.domain.repository.IFormTreeRepository
import com.lumiform.domain.usecase.GetContentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @created 17/07/2025 - 1:40 AM
 * @project FormTree
 * @author adell
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * Provides GetContentUseCase instance
     *
     * @param formTreeRepository IFormTreeRepository object [IFormTreeRepository]
     * @return GetContentUseCase object [GetContentUseCase]
     */
    @Provides
    fun provideContentUseCase(formTreeRepository: IFormTreeRepository): GetContentUseCase {
        return GetContentUseCase(formTreeRepository)
    }
}