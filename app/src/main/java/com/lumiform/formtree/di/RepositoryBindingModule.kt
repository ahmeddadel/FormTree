package com.lumiform.formtree.di

import com.lumiform.data.repository.FormTreeRepositoryImpl
import com.lumiform.domain.repository.IFormTreeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @created 17/07/2025 - 1:38 AM
 * @project FormTree
 * @author adell
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {

    /**
     * Binds the implementation of [IFormTreeRepository] to its concrete implementation [FormTreeRepositoryImpl].
     * This allows for dependency injection to provide the repository implementation
     * without needing to know the concrete class.
     *
     * @param  formTreeRepositoryImpl the implementation of [IFormTreeRepository] to be bound.
     * @return an instance of [IFormTreeRepository] which is the interface for the repository.
     */
    @Binds
    abstract fun bindFormTreeRepository(formTreeRepositoryImpl: FormTreeRepositoryImpl): IFormTreeRepository
}