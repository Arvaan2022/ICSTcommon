package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CommonResponse(
    @SerializedName("message")
    val message: String?, // Check your email - We have just sent you an email. To verify your email tap the link in the email and return to the app to sign in.Check your span/junk mail.
    @SerializedName("status_code")
    val statusCode: Int? // 200
)