package com.rehza.storyapp.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.google.android.gms.maps.model.LatLng
import com.rehza.storyapp.data.api.*
import com.rehza.storyapp.data.paging.StoryRemoteMediator
import com.rehza.storyapp.util.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getStoriesWithLocation(): LiveData<Resource<GetAllStoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getAllStories(2)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("ListStoryViewModel", "getStoriesWithLocation: ${e.message.toString()} ")
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun addNewStory(file: MultipartBody.Part, description: RequestBody, latLng: LatLng?): LiveData<Resource<AddNewStoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.addNewStory(file, description, latLng?.latitude, latLng?.longitude)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e("CreateStoryViewModel", "postStory: ${e.message.toString()}")
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String): LiveData<Resource<RegisterResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "postSignUp: ${e.message.toString()}")
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Resource<LoginResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "postLogin: ${e.message.toString()}")
            emit(Resource.Error(e.message.toString()))
        }
    }
}