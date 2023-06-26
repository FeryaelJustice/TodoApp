package com.feryaeldev.todoapp.di

import android.content.Context
import androidx.room.Room
import com.feryaeldev.todoapp.data.db.TaskDao
import com.feryaeldev.todoapp.data.db.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideTaskDao(todoDatabase: TodoDatabase): TaskDao = todoDatabase.taskDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(appContext, TodoDatabase::class.java, "todo_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}