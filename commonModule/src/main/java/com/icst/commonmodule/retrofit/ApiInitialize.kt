package com.icst.commonmodule.retrofit




import com.icst.commonmodule.BuildConfig
import com.icst.commonmodule.app.App
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiInitialize {

    private val requestHeader: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    ) else HttpLoggingInterceptor()
                )
                .build()
        }

    fun initialize(url:String): ApiInterface {
       return Retrofit.Builder()
            .baseUrl(url)
            .client(requestHeader)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    val apiImpl by lazy {
        initialize(App.url)
    }
}