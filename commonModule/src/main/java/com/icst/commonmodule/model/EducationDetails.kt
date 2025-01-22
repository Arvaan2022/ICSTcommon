package com.icst.commonmodule.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class EducationDetails(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("message")
    var message: String,
    @SerializedName("status_code")
    var statusCode: Int
) : Serializable {
    @Keep
    data class Data(
        @SerializedName("category_id")
        var categoryId: String,
        @SerializedName("category_name")
        var categoryName: String,
        @SerializedName("category_video")
        var categoryVideo: List<CategoryVideo>
    ) : Serializable
}