package com.feryaeldev.todoapp.data.model

import com.google.gson.annotations.SerializedName

// API DATA MODEL
data class ApiResponse(
    @SerializedName("message") val message: String
)

// Local TASKS Data Model
data class Task(
    val id: Long = System.currentTimeMillis(),
    var name: String,
    var selected: Boolean
)