package com.icst.commonmodule.repository.reminder

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.checkNetworkAvailableOrNot
import retrofit2.Response

class ReminderRepository{

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: ReminderRepository? = null
        fun getInstance(): ReminderRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = ReminderRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun listScheduleApiCall(
        month: Int,
        year: Int,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.listScheduleApiCall(
                month = month,
                year = year,
            )
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

    suspend fun deleteScheduleApiCall(
        id: Int,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.deleteScheduleApiCall(
                id = id,
            )
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