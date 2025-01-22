package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class EducationList(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status_code")
    val statusCode: Int
):Serializable {
    @Keep
    data class Data(
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("category_name")
        val categoryName: String,
        @SerializedName("category_video")
        val categoryVideo: List<CategoryVideo>
    ):Serializable
}