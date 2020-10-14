package com.sample.demo.koin

import android.content.Context
import com.sample.demo.BuildConfig
import com.sample.demo.api.NetworkApi
import com.sample.demo.utils.RetrofitFactory
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideRetrofit(get()) }
    single { provideService(get()) }
}

private fun provideRetrofit(context: Context) = RetrofitFactory.getRetrofit(context, BuildConfig.SERVICE_ENDPOINT)

fun provideService(retrofit: Retrofit): NetworkApi {
    return retrofit.create(NetworkApi::class.java)
}