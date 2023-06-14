package com.rehza.storyapp.ui.story.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rehza.storyapp.data.local.StoryRepository
import com.rehza.storyapp.data.api.ListStoryItem
import com.rehza.storyapp.util.Resource

class ListStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return repository.getAllStories().cachedIn(viewModelScope)
    }
}