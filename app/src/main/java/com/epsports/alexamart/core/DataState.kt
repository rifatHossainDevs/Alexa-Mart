package com.epsports.alexamart.core

sealed class DataState<T>(val message: String? = null, val data: T? = null) {
    class Loading<T>: DataState<T>()
    class Success<T>(jData: T?): DataState<T>(null, jData)
    class Error<T>(message: String): DataState<T>(message)
}