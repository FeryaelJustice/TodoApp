package com.feryaeldev.todoapp.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: String,
    @SerializedName("capital") val capital: String,
    @SerializedName("region") val region: String,
    @SerializedName("population") val population: Int
)

data class ApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("countries") val countries: List<Country>
)