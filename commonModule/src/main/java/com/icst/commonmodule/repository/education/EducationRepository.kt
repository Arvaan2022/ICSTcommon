package com.icst.commonmodule.repository.education

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.isNetWork
import retrofit2.Response

class EducationRepository {

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: EducationRepository? = null
        fun getInstance(): EducationRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = EducationRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getEducationData(
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (isNetWork(context)) {
            val response = apiClient.getEducationData()
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