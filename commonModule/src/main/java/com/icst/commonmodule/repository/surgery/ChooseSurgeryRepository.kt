package com.icst.commonmodule.repository.surgery

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.isNetWork
import retrofit2.Response

class ChooseSurgeryRepository {

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: ChooseSurgeryRepository? = null
        fun getInstance(): ChooseSurgeryRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = ChooseSurgeryRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getRegisterSurgeryList(context: Context): Resource<Any?> {
        val responseData: ApiResponseData = if (isNetWork(context)) {
            val response = apiClient.getRegisterSurgeryList()
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
        return Constant.handleApiData(responseData)
    }


    // -------------------------------------- surgeryChangeApiCall


    suspend fun surgeryChangeApiCall(id: String,context: Context): Resource<Any?> {
        val responseData: ApiResponseData = if (isNetWork(context)) {
            val response = apiClient.surgeryChangeApiCall(id = id)
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
        return Constant.handleApiData(responseData)
    }

}