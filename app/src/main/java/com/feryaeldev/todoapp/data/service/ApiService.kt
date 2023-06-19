package com.feryaeldev.todoapp.data.service

import com.feryaeldev.todoapp.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/name")
    suspend fun getApiResponse(): Response<ApiResponse>

    @GET("region/europe")
    suspend fun getCountries(): Response<ApiResponse>
}