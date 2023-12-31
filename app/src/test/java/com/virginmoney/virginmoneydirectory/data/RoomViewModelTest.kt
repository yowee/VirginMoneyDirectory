package com.virginmoney.virginmoneydirectory.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.virginmoney.virginmoneydirectory.TestUtil.Companion.getDummyRoomModel
import com.virginmoney.virginmoneydirectory.ui.room.RoomViewModel
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel
import com.virginmoney.virginmoneydirectory.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RoomViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val standardDispatcher = StandardTestDispatcher()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel: RoomViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = RoomViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `verify getRoomData function retrieves room data`() = runBlocking {
        // Given
        val roomData = RoomModel()

        roomData.add(getDummyRoomModel())

        `when`(repository.getRooms()).thenReturn(roomData)

        // When
        viewModel.getRoomData()
        standardDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(roomData, viewModel.roomLiveData.value)
    }
}
