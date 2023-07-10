package com.virginmoney.virginmoneydirectory.data.model.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.virginmoney.virginmoneydirectory.TestUtil
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModelItemModel
import com.virginmoney.virginmoneydirectory.data.remote.APICall
import org.junit.Assert.*


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RepositoryImplTest {

    private lateinit var repository: Repository

    @Mock
    lateinit var apiCall: APICall

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = RepositoryImpl(apiCall)
    }

    @Test
    fun `verify getContacts() function returns expected contact model`() = runBlocking {
        // Given
        val expectedContactModel = ContactModel()
        expectedContactModel.add(TestUtil.getDummyContactModel())
        `when`(apiCall.getContacts()).thenReturn(expectedContactModel)

        // When
        val actualContactModel = repository.getContacts()

        // Then
        assertEquals(expectedContactModel, actualContactModel)
    }

    @Test
    fun `verify getRooms() function returns expected room model`() = runBlocking {
        // Given
        // Given
        val expectedRoomModel = RoomModel()
        expectedRoomModel.add(TestUtil.getDummyRoomModel())
        `when`(apiCall.getRooms()).thenReturn(expectedRoomModel)

        // When
        val actualRoomModel = repository.getRooms()

        // Then
        assertEquals(expectedRoomModel, actualRoomModel)
    }

    @Test
    fun `verify getContacts() function returns non-null results`() = runBlocking {
        // Given
        val expectedContactModel = ContactModel()
        expectedContactModel.add(TestUtil.getDummyContactModel())
        `when`(apiCall.getContacts()).thenReturn(expectedContactModel)

        // When
        val actualContactModel = repository.getContacts()

        // Then
        assertNotNull(actualContactModel)
    }

    @Test
    fun `verify getRooms() function returns non-null results`() = runBlocking {
        // Given
        val expectedRoomModel = RoomModel()
        expectedRoomModel.add(TestUtil.getDummyRoomModel())
        `when`(apiCall.getRooms()).thenReturn(expectedRoomModel)

        // When
        val actualRoomModel = repository.getRooms()

        // Then
        assertNotNull(actualRoomModel)
    }
}


