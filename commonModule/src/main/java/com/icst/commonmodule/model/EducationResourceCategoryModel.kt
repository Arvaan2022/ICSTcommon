package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class EducationResourceCategoryModel(
    @SerializedName("status_code")
    val statusCode: Int, // 200
    @SerializedName("message")
    val message: String, // Education resource category list
    @SerializedName("data")
    val `data`: List<Data>?
) :Serializable {
    @Keep
    data class Data(
        @SerializedName("title")
        val title: String, // Employment & Education
        @SerializedName("sub_title")
        val subTitle: String, // These are resources, links & videos from external sites identified as useful by Long COVID patient groups.
        @SerializedName("cat_id")
        val catId: Int, // 8
        @SerializedName("icon_url")
        val iconUrl: String // http://3.8.102.75/covid19/public/uploads/CatIcon/97049e19ebd0556186204582a1bacda61616513142235.png
    ) :Serializable
}