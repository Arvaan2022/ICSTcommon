package com.icst.commonmodule.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Contact(
    var first_name: String,
    var last_name: String,
    var place_work: String,
    var with: String,
    var phone: String,
    var email: String
) : Serializable
