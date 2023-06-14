package com.rehza.storyapp.data.api

import android.content.Context
import com.rehza.storyapp.data.local.StoryDatabase
import com.rehza.storyapp.data.local.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService(context)
        return StoryRepository(database, apiService)
    }
}