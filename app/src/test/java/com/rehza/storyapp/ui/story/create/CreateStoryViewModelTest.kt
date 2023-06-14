package com.rehza.storyapp.ui.story.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.rehza.storyapp.data.api.AddNewStoryResponse
import com.rehza.storyapp.data.local.StoryRepository
import com.rehza.storyapp.util.Resource
import com.rehza.storyapp.utils.DataDummy
import com.rehza.storyapp.utils.getOrAwaitValue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class CreateStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var createStoryViewModel: CreateStoryViewModel
    private val dummyResponse = DataDummy.generateDummyCreateStory()

    @Before
    fun setUp() {
        createStoryViewModel = CreateStoryViewModel(storyRepository)
    }

    @Test
    fun `when add new story Should Not Null and return success`() {
        val description = "Description".toRequestBody("text/plain".toMediaType())
        val latLng: LatLng? = null
        val requestBody = Mockito.mock(File::class.java).asRequestBody("image/jpg".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData(
            "photo name",
            "photo.jpg",
            requestBody
        )

        val expectedPostResponse = MutableLiveData<Resource<AddNewStoryResponse>>()
        expectedPostResponse.value = Resource.Success(dummyResponse)

        Mockito.`when`(storyRepository.addNewStory(file, description, latLng)).thenReturn(expectedPostResponse)

        val actualResponse = createStoryViewModel.addNewStory(file, description, latLng).getOrAwaitValue()
        Mockito.verify(storyRepository).addNewStory(file, description, latLng)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Resource.Success)
        assertEquals(dummyResponse.error, (actualResponse as Resource.Success).data.error)
    }

    @Test
    fun `when add new story with null file and description, should return failed`() {
        val description = "Description".toRequestBody("text/plain".toMediaType())
        val latLng: LatLng? = null
        val requestBody = Mockito.mock(File::class.java).asRequestBody("image/jpg".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData(
            "photo name",
            "photo.jpg",
            requestBody
        )

        val expectedPostResponse = MutableLiveData<Resource<AddNewStoryResponse>>()
        expectedPostResponse.value = Resource.Error("Null Parameter")
        Mockito.`when`(storyRepository.addNewStory(file, description, latLng)).thenReturn(expectedPostResponse)

        val actualResponse = createStoryViewModel.addNewStory(file, description, latLng).getOrAwaitValue()
        Mockito.verify(storyRepository).addNewStory(file, description, latLng)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Resource.Error)
    }
}