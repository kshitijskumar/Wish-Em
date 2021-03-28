package com.example.wishem.utils

sealed class Result<out T> {

    object Success : Result<Nothing>()
    data class Error(val msg: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object Idle : Result<Nothing>()
}