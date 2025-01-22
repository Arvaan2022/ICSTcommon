package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.icst.commonmodule.R
import java.io.Serializable

@Keep
data class EducationContent(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status_code")
    val statusCode: Int
) : Serializable {
    @Keep
    data class Data(
        @SerializedName("educationData")
        var educationData: List<EducationData>,
        @SerializedName("educationfuture")
        var educationfuture: Educationfuture
    ) : Serializable {
        @Keep
        data class EducationData(
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
            var videoUrl: String
        ) : Serializable {
            fun setDrawable(): Int {
                return if (type == "video" || videoType == "Y_id") R.drawable.ic_play_blue_bg else R.drawable.ic_note_hamburger
            }
            fun playIconHideOrGone(): Boolean {
                return type == "video" || videoType=="Y_id"
            }

        }

        @Keep
        data class Educationfuture(
            @SerializedName("content")
            val content: String?,
            @SerializedName("hour")
            val hour: Int,
            @SerializedName("image_url")
            val imageUrl: String,
            @SerializedName("min")
            val min: Int,
            @SerializedName("sec")
            val sec: Int,
            @SerializedName("sub_title")
            val subTitle: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("video_url")
            val videoUrl: String
        ) : Serializable {
            fun getImage(): String {
                return if (!imageUrl.isNullOrEmpty()) imageUrl else "https://fastly.picsum.photos/id/819/200/200.jpg?hmac=nCwO4yKGbs8354aS0yf974UlPFBF_gwUSNazar7yBhk"
            }

            fun playIconHideOrGone(): Boolean {
                return type == "video"
            }
        }
    }
}