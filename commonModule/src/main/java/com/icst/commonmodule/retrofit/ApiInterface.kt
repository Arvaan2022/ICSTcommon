package com.icst.commonmodule.retrofit

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
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @Headers("accept:application/json")
    @GET("get-gp-hospital-category")
    suspend fun getGpHospitalCategory(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String
    ): Response<GetGPHospitalCategoryModel>

    @Headers("accept:application/json")
    @GET("education-home")              //  RUN
    suspend fun getEducationContent(
        @Header("Authorization") token: String,
        @Header("lan") language: String
    ): Response<EducationContent>

    @GET("education-detail")
    @Headers("accept:application/json")
    suspend fun getCategoryVideos(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Query("educationDetailId") inId: Int
    ): Response<EducationDetails>

    @Headers("accept:application/json")
    @GET("get-education-video-count")
    suspend fun getVideoCount(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Query("id") categoryId: String,
        @Query("slug") slug: String // ALL, CATEGORY, MEDICATION, SPACER, TRIGGER, SEARCH
    ): Response<GetVideoCountModel>

    @FormUrlEncoded
    @Headers("accept:application/json")
    @POST("store-education-video-count")
    suspend fun storeVideoCount(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Field("video_id") videoId: String
    ): Response<CommonResponse>

    @Headers("accept:application/json")
    @GET("get-task-education-video/{id}")
    suspend fun getTaskEducationVideoApi(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Path("id") id: String,
    ): Response<EducationVideoTaskModel>

    @Headers("accept:application/json")
    @GET("education-search")
    suspend fun getEducationVideoList(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Query("name") name: String
    ): Response<EducationList>


    @GET("get-education-resource-category")
    @Headers("accept:application/json")
    suspend fun getEducationResourceCategory(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String
    ): Response<EducationResourceCategoryModel>


    @Headers("accept:application/json")
    @GET("get-education-resource")
    suspend fun getEducationResource(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Query("id") id: String
    ): Response<EducationResourceModel>

    @Headers("accept:application/json")
    @GET("list-contact")
    suspend fun contactList(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String
    ): Response<ContactList>

    @FormUrlEncoded
    @Headers("accept:application/json")
    @POST("delete-contact")
    suspend fun deleteContact(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Field("id") id: Int
    ): Response<CommonResponse>

    @Headers("accept:application/json")
    @POST("add-contact")
    suspend fun addContact(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Body contact: Contact
    ): Response<CommonResponse>


    @FormUrlEncoded
    @Headers("accept:application/json")
    @POST("list-reminder")
    suspend fun listSchedule(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Field("month") month: Int,
        @Field("year") year: Int
    ): Response<ListScheduleModel>

    @FormUrlEncoded
    @Headers("accept:application/json")
    @POST("delete-reminder")
    suspend fun deleteSchedule(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Field("id") id: Int
    ): Response<CommonResponse>


    @Headers("accept:application/json")
    @GET("get-reminder")
    suspend fun getSchedule(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String
    ): Response<ScheduleEventModel>

    @FormUrlEncoded
    @Headers("accept:application/json")
    @POST("add-reminder")
    suspend fun addSchedule(
        @Header("Authorization") token: String,
        @Header("lan") language: String,
        @Field("reminder") event: String,
        @Field("location") location: String,
        @Field("date") date: String,
        @Field("end_date") end_date: String,
        @Field("time") time: String,
        @Field("shedule") schedule: String,
        @Field("shedule_day") schedule_day: String,
        @Field("other") other: String
    ): Response<CommonResponse>


    @FormUrlEncoded
    @Headers("accept:application/json")
    @POST("edit-reminder")
    suspend fun editSchedule(
        @Header("Authorization") token: String,
        @Header("lan") language: String,
        @Field("reminder") event: String,
        @Field("location") location: String,
        @Field("date") date: String,
        @Field("end_date") end_date: String,
        @Field("time") time: String,
        @Field("shedule") schedule: String,
        @Field("shedule_day") schedule_day: String,
        @Field("other") other: String,
        @Field("id") id: String
    ): Response<CommonResponse>


    @GET("surgery-history")
    @Headers("accept:application/json")
    suspend fun getSurgeryHistory(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String
    ): Response<GpSurgeryModel>

    @FormUrlEncoded
    @Headers("accept:application/json")
    @POST("surgery-change")
    suspend fun surgeryChange(
        @Header("Authorization") inToken: String,
        @Header("lan") language: String,
        @Field("surgery_id") id: String
    ): Response<CommonResponse>

    @GET("surgery-list")
    @Headers("accept:application/json")
    suspend fun getRegisterSurgeryList(@Header("lan") language: String): Response<RegisterSurgeryList>

}