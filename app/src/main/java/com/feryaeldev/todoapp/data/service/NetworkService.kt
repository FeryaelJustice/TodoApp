package com.feryaeldev.todoapp.data.service

import com.feryaeldev.todoapp.data.model.ApiResponse
import retrofit2.Response
import javax.inject.Inject

class NetworkService @Inject constructor(private val apiService: ApiService) {
    suspend fun getApiResponse(): Response<ApiResponse> = apiService.getApiResponse()
}