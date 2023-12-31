package com.virginmoney.virginmoneydirectory.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.virginmoney.virginmoneydirectory.ui.contact.ContactViewModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.data.repository.Repository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
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
    private val standardDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ContactViewModel


    @Mock
    lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ContactViewModel(repository)
        Dispatchers.setMain(standardDispatcher)
    }

    @Test
    fun `verify getArticleData function retrieves contact data`() = runTest {
        val contactData = ContactModel()
        contactData.add(getObject())


        `when`(repository.getContacts()).thenReturn(contactData)

        viewModel.getArticleData()
        standardDispatcher.scheduler.advanceUntilIdle()

        assertEquals(contactData, viewModel.contactLiveData.value)
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
