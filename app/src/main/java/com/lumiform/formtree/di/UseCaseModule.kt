package com.lumiform.formtree.di

import android.content.Context
import com.lumiform.domain.repository.IFormTreeRepository
import com.lumiform.domain.usecase.GetContentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
     * @param context Application context [Context]
     * @param formTreeRepository IFormTreeRepository object [IFormTreeRepository]
     * @return GetContentUseCase object [GetContentUseCase]
     */
    @Provides
    fun provideContentUseCase(
        @ApplicationContext context: Context,
        formTreeRepository: IFormTreeRepository
    ): GetContentUseCase {
        return GetContentUseCase(context, formTreeRepository)
    }
}