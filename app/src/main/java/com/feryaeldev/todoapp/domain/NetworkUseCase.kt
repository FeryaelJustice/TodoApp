package com.feryaeldev.todoapp.domain

import com.feryaeldev.todoapp.data.service.NetworkService
import javax.inject.Inject

class NetworkUseCase @Inject constructor(private val networkService: NetworkService) {
    suspend fun getApiResponse() = networkService.getApiResponse()
}