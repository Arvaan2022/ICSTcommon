package com.icst.commonmodule.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class GpSurgeryModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status_code")
    val statusCode: Int
): Serializable {
    @Keep
    data class Data(
        @SerializedName("action")
        val action: String,
        @SerializedName("date")
        val date: String,
        @SerializedName("surgery_name")
        val surgeryName: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("user_id")
        val userId: String
    ): Serializable
}