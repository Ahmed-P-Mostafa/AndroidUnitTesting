package com.polotika.androidunittesting.utils

data class Resource<out T>(val status: Status, val data: T?= null, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?=null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?=null): Resource<T> {
            return Resource(status = Status.LOADING, data =  data, message = null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}