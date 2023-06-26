package com.feryaeldev.todoapp.data.model

import com.google.gson.annotations.SerializedName

// DOMAIN DATA MODELS, but we place it in data to have it more organised

// API DATA MODELS
// Response
data class ApiResponse(
    @SerializedName("message") val message: String
)

// LOCAL DATA MODELS
// Tasks
data class Task(
    val id: Long = System.currentTimeMillis(),
    var name: String,
    var selected: Boolean
)