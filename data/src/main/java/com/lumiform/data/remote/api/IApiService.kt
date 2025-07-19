package com.lumiform.data.remote.api

import com.lumiform.data.remote.dto.ContentItemDto
import retrofit2.http.GET

/**
 * @created 16/07/2025 - 11:58 PM
 * @project FormTree
 * @author adell
 */
interface IApiService {

    @GET("f118b9f0-6f84-435e-85d5-faf4453eb72a")
    suspend fun fetchContent(): ContentItemDto
}
