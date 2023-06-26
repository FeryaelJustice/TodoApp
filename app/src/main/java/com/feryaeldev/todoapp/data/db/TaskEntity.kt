package com.feryaeldev.todoapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    var selected: Boolean = false
)
