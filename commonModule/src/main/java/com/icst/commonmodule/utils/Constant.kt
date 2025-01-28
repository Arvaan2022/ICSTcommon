package com.icst.commonmodule.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.icst.commonmodule.retrofit.ApiResponseData
import com.icst.commonmodule.retrofit.Resource
import org.json.JSONObject

object Constant {

    const val FROM_EDUCATION = "FROM_EDUCATION"
    const val ASTHMA_VIDEO_POSITION = "VIDEO_POSITION"
    const val ASTHMA_VIDEO_LIST = "VIDEO_LIST"
    const val FROM_INTENT = "FROM_INTENT"




    const val EDU_RESOURCE_ID = "EDU_RESOURCE_ID"
    const val EDU_RESOURCE_TITLE = "EDU_RESOURCE_TITLE"
    const val EDU_RESOURCE_SUBTITLE = "EDU_RESOURCE_SUBTITLE"

    const val RESOURCE_PDF_LINK = "RESOURCE_PDF_LINK"
    const val RESOURCE_URL_LINK = "RESOURCE_URL_LINK"

    const val KEY_SCHEDULE_EVENT_NAME: String = "KEY_SCHEDULE_EVENT_NAME"
    const val KEY_SCHEDULE_DATE: String = "KEY_SCHEDULE_DATE"
    const val KEY_SCHEDULE_END_DATE: String = "KEY_SCHEDULE_END_DATE"
    const val KEY_SCHEDULE_END_TIME: String = "KEY_SCHEDULE_END_TIME"
    const val KEY_SCHEDULE_LOCATION: String = "KEY_SCHEDULE_LOCATION"
    const val KEY_SCHEDULE_SCHEDULE: String = "KEY_SCHEDULE_SCHEDULE"
    const val KEY_SCHEDULE_SCHEDULE_DAY: String = "KEY_SCHEDULE_SCHEDULE_DAY"
    const val KEY_SCHEDULE_ID: String = "KEY_SCHEDULE_ID"
    const val KEY_SCHEDULE_EVENT_ID: String = "KEY_SCHEDULE_EVENT_ID"
    const val KEY_SCHEDULE_INTENT_TYPE: String = "KEY_SCHEDULE_INTENT_TYPE"
    const val SCHEDULE_INTENT_TYPE_VIEW: String = "SCHEDULE_INTENT_TYPE_VIEW"
    const val SCHEDULE_INTENT_TYPE_EDIT: String = "SCHEDULE_INTENT_TYPE_EDIT"
    const val SELECTED_SCHEDULE = "schedule"
    const val SELECTED_NEVER = "never"

    private val _logOutAction = MutableLiveData(false)
    val logOutAction: LiveData<Boolean>
        get() {
            return _logOutAction
        }


    fun handleApiData(apiData: ApiResponseData?): Resource<Any?> {

        when (apiData) {
            null -> {
                return Resource.Failure(
                    isNetwork = false,
                    errorCode = null,
                    errorMessage = "Something went wrong",
                    errorBody = null
                )
            }

            else -> {
                if (apiData.isNetworkAvailable == false) {
                    return Resource.Failure(
                        isNetwork = false,
                        errorCode = null,
                        errorMessage = "Please check your Internet Connection",
                        errorBody = null
                    )
                }
                when (apiData.apiStatus) {

                    200 -> {
                        return Resource.Success(apiData.responseBody)
                    }

                    202 -> {
                        return Resource.Failure(
                            false,
                            errorCode = apiData.response?.code(),
                            errorBody = apiData.responseBody,
                            errorMessage = apiData.message.toString())

                    }

                    400 -> {
                        val errorBodyToJson = JSONObject(
                            apiData.response?.errorBody()?.charStream()?.readText().toString()
                        )
                        return Resource.Failure(
                            false,
                            errorCode = apiData.response?.code(),
                            errorBody = apiData.responseBody,
                            errorMessage = errorBodyToJson.getString("message")
                        )
                    }

                    401 -> {
                        val errorBodyToJson = JSONObject(
                            apiData.response?.errorBody()?.charStream()?.readText().toString()
                        )
                        return Resource.Failure(
                            false,
                            errorCode = apiData.response?.code(),
                            errorBody = apiData.responseBody,
                            errorMessage = errorBodyToJson.getString("message")
                        )

                    }

                    422 -> {
                        val errorBodyToJson = JSONObject(
                            apiData.response?.errorBody()?.charStream()?.readText().toString()
                        )
                        return Resource.Failure(
                            false,
                            errorCode = apiData.response?.code(),
                            errorBody = apiData.responseBody,
                            errorMessage = apiData.response?.message() /*errorBodyToJson.getString("message")*/
                        )

                    }

                    500 -> {
                        return Resource.Failure(
                            false,
                            errorCode = apiData.response?.code(),
                            errorBody = apiData.responseBody,
                            errorMessage = apiData.response?.message()
                        )
                    }

                    else -> {
                        return Resource.Failure(
                            false,
                            errorCode = apiData.response?.code(),
                            errorBody = null,
                            errorMessage = apiData.message.toString()
                        )
                    }
                }
            }
        }
    }


    fun handlerApiError(failure: Resource.Failure, context: Context, view: View) {

        when {
            failure.isNetwork -> {
                Toast.makeText(
                    context,
                    "Please check your Internet Connection",
                    Toast.LENGTH_SHORT
                ).show()

            }

            failure.errorCode == 422 || failure.errorCode == 202 -> {

                Toast.makeText(
                    context,
                    failure.errorMessage.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            failure.errorCode == 401 ->{
                Toast.makeText(
                    context,
                    failure.errorMessage.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            failure.errorCode == 400 -> {
                Toast.makeText(
                    context,
                    failure.errorMessage.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            failure.errorCode == 500 -> {
                Toast.makeText(
                    context,
                    failure.errorMessage.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        _logOutAction.value = failure.errorCode==401
        _logOutAction.value = false

    }
}