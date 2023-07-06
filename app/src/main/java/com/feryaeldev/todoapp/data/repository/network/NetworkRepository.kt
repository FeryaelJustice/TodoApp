package com.feryaeldev.todoapp.data.repository.network

import com.feryaeldev.todoapp.data.model.ApiResponse
import com.feryaeldev.todoapp.data.service.NetworkService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val networkService: NetworkService) {
    suspend fun getApiResponse(): Response<ApiResponse> = networkService.getApiResponse()
}