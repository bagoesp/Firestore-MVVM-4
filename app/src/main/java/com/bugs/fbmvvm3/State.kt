package com.bugs.fbmvvm3

sealed class State<T>{
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Failed<T>(val message: String) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data:T) = Success<T>(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}
