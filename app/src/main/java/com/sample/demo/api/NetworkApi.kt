package com.sample.demo.api

import com.sample.demo.model.BaseResponse
import com.sample.demo.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi {

    @GET("users")
    fun getUsers(): Observable<Response<BaseResponse<List<User>>>>

    @POST("users")
    fun onCreateUser(@Body user: User): Observable<Response<BaseResponse<User>>>
}