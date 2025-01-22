package com.icst.commonmodule.repository.addContact

import android.content.Context
import com.icst.commonmodule.model.Contact
import com.icst.commonmodule.retrofit.ApiClient
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handleApiData
import com.icst.commonmodule.utils.isNetWork
import retrofit2.Response

class AddContactRepository{

    private var apiClient: ApiClient = ApiClient()

    companion object {
        private var mInstance: AddContactRepository? = null
        fun getInstance(): AddContactRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = AddContactRepository()
                }
            }
            return mInstance!!
        }
    }

    suspend fun addContactApiCall(
        contact: Contact,
        context: Context
    ): Resource<Any?> {
        val responseData: ApiResponseData = if (isNetWork(context)) {
            val response = apiClient.addContactApiCall(contact)
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