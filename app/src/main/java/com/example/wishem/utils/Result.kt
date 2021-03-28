package com.example.wishem.utils

sealed class Result<out T> {

    object EmptySuccess : Result<Nothing>()
    data class Error(val msg: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object Idle : Result<Nothing>()
    data class Success<T>(val data : T) : Result<T>()
}