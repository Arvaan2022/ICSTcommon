package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class NormalValueDataModel(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String, // normal value setting data
    @SerializedName("status_code")
    val statusCode: Int // 200
) {
    @Keep
    data class Data(
        @SerializedName("overview")
        val overview: List<Overview>?,
        @SerializedName("video_data")
        val videoData: List<VideoData>?
    ) {
        @Keep
        data class Overview(
            @SerializedName("description")
            val description: String?, // <h2>What is Lorem Ipsum?</h2><p><strong>Lorem Ipsum</strong>&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum</p>
            @SerializedName("image_url")
            val imageUrl: String?, // http://3.8.102.75/pam/public/uploads/NormalValue/d4b387894df20ad1bf4c30a17a97e7801621252135804.png
            @SerializedName("title")
            val title: String? // Primary biliary cholangitis liver disease
        )

        @Keep
        data class VideoData(
            @SerializedName("des")
            val des: String?, // <p>check one more time</p>
            @SerializedName("hour")
            val hour: String?, // 0
            @SerializedName("min")
            val min: String?, // 1
            @SerializedName("sec")
            val sec: String?, // 3
            @SerializedName("thmub_url")
            val thmubUrl: String?, // https://embed-ssl.wistia.com/deliveries/83208b520e3427d89077b243e0780f01.jpg?image_crop_resized=200x120
            @SerializedName("title")
            val title: String?, // Annual review 1
            @SerializedName("video_url")
            val videoUrl: String? // http://embed.wistia.com/deliveries/17b15c641cb1b59ed94515e16d95cc27.bin
        ) : Serializable
    }
}