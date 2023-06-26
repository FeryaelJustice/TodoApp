package com.feryaeldev.todoapp.domain.usecase.network

import com.feryaeldev.todoapp.domain.repository.network.NetworkRepository
import javax.inject.Inject

class GetApiResponseUseCase @Inject constructor(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke() = networkRepository.getApiResponse()
}