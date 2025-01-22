package com.icst.commonmodule.repository.gphospital


import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.isNetWork
import retrofit2.Response

class GpHospitalRepository {

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: GpHospitalRepository? = null
        fun getInstance(): GpHospitalRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = GpHospitalRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getGpHospitalCategoryApiCall(
        language: String,
        token: String,
        url: String,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (isNetWork(context)) {
            val response = apiClient.getGpHospitalCategoryApiCall(
                language = language,
                token = token, url = url
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
        return Constant.handleApiData(responseData)
    }

}