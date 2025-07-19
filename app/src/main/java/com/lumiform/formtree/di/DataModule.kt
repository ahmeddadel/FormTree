package com.lumiform.formtree.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.lumiform.data.local.db.FormTreeDatabase
import com.lumiform.data.local.db.dao.CachedJsonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @created 17/07/2025 - 12:10 AM
 * @project FormTree
 * @author adell
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    /**
     * Provides Room database instance
     *
     * @param context Application context [Context]
     * @return FormTreeDatabase object [FormTreeDatabase]
     */
    @Provides
    @Singleton
    fun provideFormTreeDatabase(@ApplicationContext context: Context): FormTreeDatabase =
        Room.databaseBuilder(context, FormTreeDatabase::class.java, "FormTree_DB").build()


    /**
     * Provides CachedJsonDao instance
     *
     * @param db FormTreeDatabase object [FormTreeDatabase]
     * @return CachedJsonDao object [CachedJsonDao]
     */
    @Provides
    @Singleton
    fun provideCachedJsonDao(db: FormTreeDatabase): CachedJsonDao =
        db.cachedJsonDao()

    /**
     * Provides Gson instance
     *
     * @return Gson object [Gson]
     */
    @Provides
    @Singleton
    fun provideGson(): Gson =
        Gson()
}
