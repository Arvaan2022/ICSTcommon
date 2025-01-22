package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class EducationVideoTaskModel(
    @SerializedName("data")
    val `data`: ArrayList<Data>?,
    @SerializedName("message")
    val message: String?, // Education video
    @SerializedName("status_code")
    val statusCode: Int? // 200
) :Serializable{
    @Keep
    data class Data(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("description")
        val descriptionWelsh: String, // <h1><strong><span style="font-size:14px"><span style="font-family:Open Sans">What Is Viral Hepatitis?</span></span></strong></h1><p><span style="font-size:14px"><span style="font-family:Open Sans">Viral hepatitis is an infection that causes&nbsp;liver&nbsp;inflammation and damage. Inflammation is swelling that occurs when tissues of the body become injured or infected. Inflammation can damage organs. Researchers have discovered several different&nbsp;viruses&nbsp;<em>NIH external link</em>&nbsp;that cause hepatitis, including hepatitis A, B, C, D, and E.</span></span></p><p><span style="font-size:14px"><span style="font-family:Open Sans">Hepatitis A&nbsp;and&nbsp;hepatitis E&nbsp;typically spread through contact with food or water that has been contaminated by an infected person&rsquo;s&nbsp;stool. People may also get hepatitis E by eating undercooked pork, deer, or shellfish.</span></span></p><p><span style="font-size:14px"><span style="font-family:Open Sans">Hepatitis B,&nbsp;hepatitis C, and&nbsp;hepatitis D&nbsp;spread through contact with an infected person&rsquo;s blood. Hepatitis B and D may also spread through contact with other body fluids. This contact can occur in many ways, including sharing drug needles or having unprotected sex.</span></span></p>
        @SerializedName("education_category_id")
        val educationCategoryId: Int?, // 2
        @SerializedName("hour")
        val hour: String?, // 0
        @SerializedName("medication")
        val medication: String?, // 2
        @SerializedName("minute")
        val min: String?, // 1
        @SerializedName("name")
        val name: String?, // What Is Viral Hepatitis?
        @SerializedName("second")
        val sec: String?, // 27
        @SerializedName("sub_title")
        val subTitle: String?, // Viral hepatitis is an infection that causes liver inflammation and damage.
        @SerializedName("type")
        val type: String?, // video
        @SerializedName("video_thumb")
        val videoThumb: String?, // https://embed-ssl.wistia.com/deliveries/42ee1c4ef3981d1ceff6f675f3cee745.jpg
        @SerializedName("video_type")
        val videoType: String?, // wistia
        @SerializedName("video_url")
        val videoUrl: String, // https://embed-ssl.wistia.com/deliveries/5a4ef1e5eeab97441debd3bd5a21422a.bin
        var isWatch:Boolean = false
    ) :Serializable
}