package com.icst.commonmodule.repository.contactList


import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.checkNetworkAvailableOrNot

import retrofit2.Response

class ContactListRepository{

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: ContactListRepository? = null
        fun getInstance(): ContactListRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = ContactListRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun contactListApiCall(context: Context): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.contactListApiCall()
            val responseBody = response.body()

            ApiResponseData(
                apiStatus = response.code(),
                message = responseBody?.message,
                responseBody = responseBody,
                response = response as Response<Any>,
            )

        } else {
            ApiResponseData(isNetworkAvailable = false)
        }
        return handleApiData(responseData)
    }

    suspend fun deleteContactApiCall(
        id: Int,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.deleteContactApiCall(id)
            val responseBody = response.body()

            ApiResponseData(
                apiStatus = response.code(),
                message = responseBody?.message,
                responseBody = responseBody,
                response = response as Response<Any>,
            )

        } else {
            ApiResponseData(isNetworkAvailable = false)
        }
        return handleApiData(responseData)
    }
}