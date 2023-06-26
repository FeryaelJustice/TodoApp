package com.feryaeldev.todoapp.data.mapper

import com.feryaeldev.todoapp.data.db.TaskEntity
import com.feryaeldev.todoapp.data.model.Task

// File to map data models to domain models

fun TaskEntity.toTask() = Task(
    id = id,
    name = name,
    selected = selected
)

fun Task.toTaskEntity() = TaskEntity(
    id = id,
    name = name,
    selected = selected
)