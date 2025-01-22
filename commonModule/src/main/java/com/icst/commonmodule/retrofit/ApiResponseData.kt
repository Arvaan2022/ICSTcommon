package com.icst.commonmodule.retrofit

import retrofit2.Response

class ApiResponseData(
    var apiStatus: Any? = null,
    val message: Any? = null,
    val response: Response<Any>? = null,
    var responseBody: Any? = null,
    val isNetworkAvailable: Boolean? = true
)