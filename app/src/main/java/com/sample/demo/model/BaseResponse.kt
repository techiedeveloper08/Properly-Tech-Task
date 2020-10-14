package com.sample.demo.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: T,
)