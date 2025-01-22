package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class GetVideoCountModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status_code")
    val statusCode: Int
) {
    @Keep
    data class Data(
        @SerializedName("is_watch")
        val isWatch: String,
        @SerializedName("video_id")
        val videoId: String
    )
}