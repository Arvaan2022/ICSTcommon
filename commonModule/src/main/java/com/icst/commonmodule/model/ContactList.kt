package com.icst.commonmodule.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class ContactList(
    var `data`: MutableList<ContactData>,
    var message: String,
    var status_code: Int
): Serializable{
    @Keep
    data class ContactData(
        var created_at: String,
        var email: String,
        var first_name: String,
        var id: Int,
        var is_delete: String,
        var last_name: String,
        var phone: String,
        var place_work: String,
        var updated_at: String,
        var user_id: String,
        var with: String
    ) : Serializable
}