package com.icst.commonmodule.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class CategoryVideo(
    @SerializedName("category_id")
    var categoryId: Int,
    @SerializedName("description")
    var description: String,
    @SerializedName("hour")
    var hour: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("minute")
    var minute: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("second")
    var second: Int,
    @SerializedName("sub_title")
    var subTitle: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("video_thumb")
    var videoThumb: String,
    @SerializedName("video_type")
    var videoType: String,
    @SerializedName("video_url")
    var videoUrl: String,
    var isWatch:Boolean = false
) : Serializable