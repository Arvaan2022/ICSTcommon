package com.icst.commonmodule.repository.education

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.checkNetworkAvailableOrNot
import retrofit2.Response

class EducationResourcesRepository {

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: EducationResourcesRepository? = null
        fun getInstance(): EducationResourcesRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = EducationResourcesRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getEducationResourceCategoryApiCall(
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.getEducationResourceCategoryApiCall()
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

    suspend fun getEducationResourceApiCall(
        id: String,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.getEducationResourceApiCall(
                id=id,
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