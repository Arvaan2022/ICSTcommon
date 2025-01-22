package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class EducationResourceModel(
    @SerializedName("status_code")
    val statusCode: Int, // 200
    @SerializedName("message")
    val message: String, // Education resource data
    @SerializedName("data")
    val `data`: List<Data>
) :Serializable{
    @Keep
    data class Data(
        @SerializedName("sub_cat_name")
        val subCatName: String, // Returning to work
        @SerializedName("resource_data")
        val resourceData: List<ResourceData>
    ) :Serializable {
        @Keep
        data class ResourceData(
            @SerializedName("type")
            val type: String, // link
            @SerializedName("link")
            val link: String, // https://www.yourcovidrecovery.nhs.uk/your-road-to-recovery/returning-to-work/
            @SerializedName("pdf_url")
            val pdfUrl: String,
            @SerializedName("title")
            val title: String, // Your COVID Recovery- returning to work
            @SerializedName("des")
            val des: String, // The NHS guide to returning to work after you have been diagnosed with Long COVID
            @SerializedName("icon_url")
            val iconUrl: String // http://3.8.102.75/covid19/public/uploads/resource/icon/d8c2c648dd981b288b5cb8738c4e0b721616512624638.png
        ) :Serializable
    }
}