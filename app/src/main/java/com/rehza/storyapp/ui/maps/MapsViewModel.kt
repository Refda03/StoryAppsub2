package com.rehza.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.rehza.storyapp.data.local.StoryRepository

class MapsViewModel (private val storyRepository: StoryRepository):ViewModel() {
    fun getStoriesWithLocation() = storyRepository.getStoriesWithLocation()
}