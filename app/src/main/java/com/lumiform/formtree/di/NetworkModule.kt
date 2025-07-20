package com.lumiform.formtree.di

import android.content.Context
import com.lumiform.data.remote.api.IApiService
import com.lumiform.formtree.BuildConfig
import com.lumiform.formtree.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @created 17/07/2025 - 1:37 AM
 * @project FormTree
 * @author adell
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides HttpLoggingInterceptor instance
     *
     * @return HttpLoggingInterceptor object [HttpLoggingInterceptor]
     */
    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    /**
     * Provides OkHttp client instance
     *
     * @return OkHttpClient object [OkHttpClient]
     */
    @Provides
    @Singleton
    fun provideOkHttpclient(
        @ApplicationContext applicationContext: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    /**
     * Provides Retrofit instance
     *
     * @param okHttpClient OkHttp client object [OkHttpClient]
     * @return Retrofit object [Retrofit]
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     * Provides IApiService instance
     *
     * @param retrofit Retrofit object [Retrofit]
     * @return IApiService object [IApiService]
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): IApiService =
        retrofit.create(IApiService::class.java)
}
