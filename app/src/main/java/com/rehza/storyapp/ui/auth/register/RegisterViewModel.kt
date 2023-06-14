package com.rehza.storyapp.ui.auth.register

import androidx.lifecycle.ViewModel
import com.rehza.storyapp.data.local.StoryRepository

class RegisterViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = storyRepository.register(name, email, password)
}
