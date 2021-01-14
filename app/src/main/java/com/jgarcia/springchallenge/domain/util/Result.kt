package com.jgarcia.springchallenge.domain.util

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: ErrorType) : Result<Nothing>()
}

sealed class ErrorType {
    object NetworkError : ErrorType()
    object TimeoutException : ErrorType()
    object UnknownError : ErrorType()
}