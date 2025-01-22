package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class ScheduleEventModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String, // Admin Activity List
    @SerializedName("status_code")
    val statusCode: Int // 200
) : Serializable {
    @Keep
    data class Data(
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String // first updarte
    ):Serializable
}