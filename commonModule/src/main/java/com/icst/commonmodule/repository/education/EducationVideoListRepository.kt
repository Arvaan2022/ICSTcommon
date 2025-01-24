package com.icst.commonmodule.repository.education

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.checkNetworkAvailableOrNot
import retrofit2.Response

class EducationVideoListRepository {

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: EducationVideoListRepository? = null
        fun getInstance(): EducationVideoListRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = EducationVideoListRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getEducationVideoList(
        name: String,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.getEducationVideoList(
                name = name,
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