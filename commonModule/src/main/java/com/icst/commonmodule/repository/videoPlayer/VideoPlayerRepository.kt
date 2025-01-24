package com.icst.commonmodule.repository.videoPlayer

import android.content.Context
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.checkNetworkAvailableOrNot
import retrofit2.Response

class VideoPlayerRepository {

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: VideoPlayerRepository? = null
        fun getInstance(): VideoPlayerRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = VideoPlayerRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun getCategoryVideos(
        id: Int,
       context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response =
                apiClient.getCategoryVideos(id = id,)
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

    suspend fun getVideoCount(
        id: String,
        slug: String,
       context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.getVideoCount(
                id = id,
                slug = slug
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

    suspend fun storeVideoCount(
        videoId: String,
       context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.storeVideoCount(id=videoId)
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

    ///////////////-----------------

    suspend fun getTaskEducationVideoApi(
        id: String,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (checkNetworkAvailableOrNot(context)) {
            val response = apiClient.getTaskEducationVideoApi(id = id)
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