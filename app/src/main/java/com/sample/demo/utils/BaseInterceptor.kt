package com.sample.demo.utils

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws


class BaseInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val locale: Locale = if (SDK_INT >= Build.VERSION_CODES.N) {
            Locale.getDefault()
        } else {
            Locale.ROOT
        }

        val builder = chain.request().newBuilder()
        builder.addHeader("Accept", "application/json")
        builder.addHeader("Accept-Language", locale.language)
        builder.addHeader("platform", "android")
        builder.addHeader("Content-Type", "application/json")
        return chain.proceed(builder.build())
    }
}