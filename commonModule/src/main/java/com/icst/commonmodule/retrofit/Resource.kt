package com.icst.commonmodule.retrofit

sealed class Resource<out T>{
    data object Loading: Resource<Nothing>()
    data object Dismiss: Resource<Nothing>()
    data class  Success<out T>(val value:T): Resource<T>()
    data class Failure(
        val isNetwork:Boolean,
        val errorCode:Int?,
        val errorMessage:String?,
        val errorBody:Any?
    ): Resource<Nothing>()
}
