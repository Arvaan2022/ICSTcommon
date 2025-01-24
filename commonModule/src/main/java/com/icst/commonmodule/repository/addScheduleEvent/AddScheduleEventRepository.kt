package com.icst.commonmodule.repository.addScheduleEvent

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.checkNetworkAvailableOrNot
import retrofit2.Response

class AddScheduleEventRepository{

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: AddScheduleEventRepository? = null
        fun getInstance(): AddScheduleEventRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = AddScheduleEventRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getScheduleApiCall(context: Context): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.getScheduleApiCall()
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

    suspend fun addScheduleApiCall(
        reminder: String,
        location: String,
        date: String,
        endDate: String,
        time: String,
        schedule: String,
        other: String,
        scheduleDay: String,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.addSchedule(   reminder = reminder,
                location = location,
                date = date,
                endDate = endDate,
                time = time,
                schedule = schedule,
                scheduleDay = scheduleDay,
                other = other)
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

    suspend fun editScheduleApiCall(
        reminder: String,
        location: String,
        date: String,
        endDate: String,
        time: String,
        schedule: String,
        other: String,
        id: String,
        scheduleDay: String,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.editSchedule(  reminder = reminder,
                location = location,
                date = date,
                endDate = endDate,
                time = time,
                schedule = schedule,
                scheduleDay = scheduleDay,
                other = other,
                id = id)
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