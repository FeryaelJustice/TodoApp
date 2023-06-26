package com.feryaeldev.todoapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM Tasks")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(task: TaskEntity)

    @Query("DELETE FROM Tasks")
    fun deleteAllTasks()

    @Transaction
    fun clearAllTables(){
        deleteAllTasks()
    }
}