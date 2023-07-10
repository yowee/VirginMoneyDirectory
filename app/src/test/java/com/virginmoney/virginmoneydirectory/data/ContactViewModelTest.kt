package com.virginmoney.virginmoneydirectory.data

import org.junit.Assert.*

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.virginmoney.virginmoneydirectory.data.ContactViewModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.data.model.repository.Repository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ContactViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel: ContactViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ContactViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `verify getArticleData function retrieves contact data`() = runBlocking {
        val contactData = ContactModel()
        contactData.add(getObject())

        val expectedData = MutableLiveData<ContactModel>()
        expectedData.postValue(contactData)
        `when`(repository.getContacts()).thenReturn(contactData)

        viewModel.getArticleData()

        assertEquals(expectedData.value, viewModel.contactLiveData.value)
    }

    fun getObject() = ContactModelItemModel(
        avatar = "https://randomuser.me/api/portraits/women/21.jpg",
        createdAt = "2022-01-24T17:02:23.729Z",
        data = null,
        email = "Crystel.Nicolas61@hotmail.com",
        favouriteColor = "pink",
        firstName = "Maggie",
        fromName = "2",
        id = "1",
        jobtitle = "Future Functionality Strategist",
        lastName = "Brekke",
        name = "jhon doe ",
        size = "2",
        to = "to",
        type = "type"
    )


}
