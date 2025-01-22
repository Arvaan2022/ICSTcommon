package com.icst.commonmodule.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class ListScheduleModel(
    @SerializedName("data")
    val `data`: List<DataItem>,
    @SerializedName("message")
    val message: String, // Event or Activity List
    @SerializedName("status_code")
    val statusCode: Int // 200
) : Serializable {
    @Keep
    data class DataItem(
        @SerializedName("date")
        val date: String, // 2021-07-13
        @SerializedName("day")
        val day: String, // Tuesday
        @SerializedName("day_date")
        val dayDate: String, // 2021-07-13
        @SerializedName("end_date")
        val endDate: String, // 2021-07-14
        @SerializedName("id")
        val id: Int, // 19
        @SerializedName("location")
        val location: String, // abc
        @SerializedName("reminder_id")
        val reminderId: String, // 7
        @SerializedName("reminder_name")
        val reminderName: String, // abc
        @SerializedName("schedule")
        val schedule: String, // never
        @SerializedName("schedule_day")
        val scheduleDay: String,
        @SerializedName("time")
        val time: String, // 10:11

        val isExpanded: Boolean = true
    ):Serializable
}