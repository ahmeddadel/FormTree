package com.lumiform.domain

/**
 * @created 18/07/2025 - 6:24 PM
 * @project FormTree
 * @author adell
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}