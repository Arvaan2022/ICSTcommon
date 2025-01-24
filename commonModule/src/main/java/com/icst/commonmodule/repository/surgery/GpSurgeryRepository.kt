package com.icst.commonmodule.repository.surgery

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.checkNetworkAvailableOrNot
import retrofit2.Response

class GpSurgeryRepository {

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: GpSurgeryRepository? = null
        fun getInstance(): GpSurgeryRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = GpSurgeryRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getSurgeryHistoryApiCall(context: Context): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.getSurgeryHistoryApiCall()
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