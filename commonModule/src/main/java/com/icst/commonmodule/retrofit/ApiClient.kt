package com.icst.commonmodule.retrofit

import com.icst.commonmodule.app.App.Companion.language
import com.icst.commonmodule.app.App.Companion.token
import com.icst.commonmodule.model.CommonResponse
import com.icst.commonmodule.model.Contact
import com.icst.commonmodule.model.ContactList
import com.icst.commonmodule.model.EducationContent
import com.icst.commonmodule.model.EducationDetails
import com.icst.commonmodule.model.EducationList
import com.icst.commonmodule.model.EducationResourceCategoryModel
import com.icst.commonmodule.model.EducationResourceModel
import com.icst.commonmodule.model.EducationVideoTaskModel
import com.icst.commonmodule.model.GetGPHospitalCategoryModel
import com.icst.commonmodule.model.GetVideoCountModel
import com.icst.commonmodule.model.GpSurgeryModel
import com.icst.commonmodule.model.ListScheduleModel
import com.icst.commonmodule.model.RegisterSurgeryList
import com.icst.commonmodule.model.ScheduleEventModel

import retrofit2.Response

class ApiClient {


    suspend fun getGpHospitalCategoryApiCall(
        language: String,
        token: String,
        url: String
    ): Response<GetGPHospitalCategoryModel> {
        return ApiInitialize.initialize(url).getGpHospitalCategory(
            language = language,
            inToken = "Bearer $token"
        )
    }

    suspend fun getEducationData(
    ): Response<EducationContent> {
        return ApiInitialize.apiImpl.getEducationContent(
            language = language,
            token = "Bearer $token"
        )
    }

    suspend fun getCategoryVideos(
         id: Int
    ): Response<EducationDetails> {
        return ApiInitialize.apiImpl.getCategoryVideos(
            language = language,
            inToken = "Bearer $token",
            inId = id
        )
    }

    suspend fun getVideoCount(
        id: String,
        slug: String,
    ): Response<GetVideoCountModel> {
        return ApiInitialize.apiImpl.getVideoCount(
            language = language,
            inToken = "Bearer $token",
            categoryId = id,
            slug = slug
        )
    }

    suspend fun storeVideoCount(
        id: String,
    ): Response<CommonResponse> {
        return ApiInitialize.apiImpl.storeVideoCount(
            language = language,
            inToken = "Bearer $token",
            videoId = id
        )
    }

    suspend fun getTaskEducationVideoApi(
        id: String,
    ): Response<EducationVideoTaskModel> {
        return ApiInitialize.apiImpl.getTaskEducationVideoApi(
            language = language,
            inToken = "Bearer $token",
            id = id
        )
    }

    suspend fun getEducationVideoList(
        name: String,
    ): Response<EducationList> {
        return ApiInitialize.apiImpl.getEducationVideoList(
            language = language,
            inToken = "Bearer $token",
            name = name
        )
    }

    suspend fun getEducationResourceCategoryApiCall(): Response<EducationResourceCategoryModel> {
        return ApiInitialize.apiImpl.getEducationResourceCategory(
            language = language,
            inToken = "Bearer $token"
        )
    }

    suspend fun getEducationResourceApiCall(
        id: String
    ): Response<EducationResourceModel> {
        return ApiInitialize.apiImpl.getEducationResource(
            language = language,
            inToken = "Bearer $token",
            id = id
        )
    }

    suspend fun contactListApiCall(): Response<ContactList> {
        return ApiInitialize.apiImpl.contactList(
            language = language,
            inToken = "Bearer $token",
        )
    }

    suspend fun deleteContactApiCall(id: Int): Response<CommonResponse> {
        return ApiInitialize.apiImpl.deleteContact(
            language = language,
            inToken = "Bearer $token",
            id = id
        )
    }

    suspend fun addContactApiCall(contact: Contact): Response<CommonResponse> {
        return ApiInitialize.apiImpl.addContact(
            language = language,
            inToken = "Bearer $token",
            contact = contact
        )
    }

    suspend fun listScheduleApiCall(month: Int, year: Int): Response<ListScheduleModel> {
        return ApiInitialize.apiImpl.listSchedule(
            language = language,
            inToken = "Bearer $token",
            month = month,
            year = year
        )
    }

    suspend fun deleteScheduleApiCall(id: Int): Response<CommonResponse> {
        return ApiInitialize.apiImpl.deleteSchedule(
            language = language,
            inToken = "Bearer $token",
            id = id
        )
    }

    suspend fun getScheduleApiCall(): Response<ScheduleEventModel> {
        return ApiInitialize.apiImpl.getSchedule(
            language = language,
            inToken = "Bearer $token",
        )
    }

    suspend fun addSchedule(
        reminder: String,
        location: String,
        date: String,
        endDate: String,
        time: String,
        schedule: String,
        other: String,
        scheduleDay: String
    ): Response<CommonResponse> {
        return ApiInitialize.apiImpl.addSchedule(
            language = language,
            token = "Bearer $token",
            event = reminder,
            location = location,
            date = date,
            end_date = endDate,
            time = time,
            schedule = schedule,
            schedule_day = scheduleDay,
            other = other

        )
    }

    suspend fun editSchedule(
        reminder: String,
        location: String,
        date: String,
        endDate: String,
        time: String,
        schedule: String,
        other: String,
        id: String,
        scheduleDay: String
    ): Response<CommonResponse> {
        return ApiInitialize.apiImpl.editSchedule(
            language = language,
            token = "Bearer $token",
            event = reminder,
            location = location,
            date = date,
            end_date = endDate,
            time = time,
            schedule = schedule,
            schedule_day = scheduleDay,
            other = other,
            id = id
        )
    }

    suspend fun getSurgeryHistoryApiCall(): Response<GpSurgeryModel> {
        return ApiInitialize.apiImpl.getSurgeryHistory(
            inToken = "Bearer $token",
            language = language
        )
    }

    suspend fun surgeryChangeApiCall(id: String): Response<CommonResponse> {
        return ApiInitialize.apiImpl.surgeryChange(
            inToken = "Bearer $token",
            language = language,
            id = id
        )
    }

    suspend fun getRegisterSurgeryList(
    ): Response<RegisterSurgeryList> {
        return ApiInitialize.apiImpl.getRegisterSurgeryList(
            language = language
        )
    }
}