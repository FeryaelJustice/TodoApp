package com.feryaeldev.todoapp.data.db

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [TaskEntity::class], version = 1, exportSchema = true)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    // DAO
    override fun clearAllTables() {
        taskDao().clearAllTables()
    }

    override fun createInvalidationTracker(): InvalidationTracker = InvalidationTracker(this)

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }
}