package com.lumiform.formtree.di

import com.google.gson.Gson
import com.lumiform.data.local.db.dao.CachedJsonDao
import com.lumiform.data.remote.api.IApiService
import com.lumiform.data.repository.FormTreeRepositoryImpl
import com.lumiform.domain.repository.IFormTreeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @created 17/07/2025 - 1:38 AM
 * @project FormTree
 * @author adell
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides IMainRepository instance
     *
     * @param apiService IApiService object [IApiService]
     * @param cachedJsonDao CachedJsonDao object [CachedJsonDao]
     * @param gson Gson object [Gson]
     * @return FormTreeRepository object [IFormTreeRepository]
     */
    @Provides
    fun provideFormTreeRepository(
        apiService: IApiService,
        cachedJsonDao: CachedJsonDao,
        gson: Gson
    ): IFormTreeRepository {
        return FormTreeRepositoryImpl(apiService, cachedJsonDao, gson)
    }
}