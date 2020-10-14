package com.sample.demo.utils

import android.content.Context
import com.sample.demo.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun getRetrofit(application: Context, API: String): Retrofit {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        httpClient.interceptors().add(BaseInterceptor())
        httpClient.interceptors().add(ConnectivityInterceptor(application))
        httpClient.interceptors().add(BasicAuthInterceptor(token = "Bearer df770ba95557f63ed585a2d41d172e5a3e04053d1b6b32119f598c0fe47a14bc"))
        httpClient.connectTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)

        return Retrofit.Builder()
                .baseUrl(API)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }
}