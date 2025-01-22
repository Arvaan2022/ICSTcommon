package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Keep
data class GetGPHospitalCategoryModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String, // gp hospital question category list
    @SerializedName("status_code")
    val statusCode: Int // 200
):Serializable {
    @Keep
    data class Data(
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("image_url")
        val imageUrl: String, // http://3.8.102.75/pam/public/uploads/GpHospitalVisit/1992460e5ed6b3e039dc83ebd4dad815162134622840.jpg
        @SerializedName("name")
        val name: String, // I am unwell and visiting my GP/Asthma nurse
        @SerializedName("sub_title")
        val subTitle: String // You are visiting your GP or your Asthma Nurse because you are unwell with your Asthma currently.
    ):Serializable
}