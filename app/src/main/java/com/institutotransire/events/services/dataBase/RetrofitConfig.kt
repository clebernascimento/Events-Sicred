package com.institutotransire.events.services.dataBase

import com.institutotransire.events.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitConfig {

    private fun config(): Retrofit {

        val httpBuilder = OkHttpClient.Builder()
        val log = HttpLoggingInterceptor()

        log.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpBuilder.addInterceptor(log)
        httpBuilder.connectTimeout(TIMEOUT_CONNECTION_SECONDS, TimeUnit.SECONDS)
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpBuilder.build())
                .build()
    }

    init {
        retrofit = config()
    }

    companion object {
        lateinit var retrofit: Retrofit
        private const val BASE_URL = BuildConfig.BASE_URL
        private const val TIMEOUT_CONNECTION_SECONDS: Long = 10
    }


}