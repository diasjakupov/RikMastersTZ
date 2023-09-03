package com.example.rikmasterstz.domain.network

sealed class Response {
    class Error(val message: String): Response()
    class Success<T>(val data: T): Response()
    object Loading: Response()
}