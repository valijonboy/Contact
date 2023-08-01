package com.valijonboy.domain.UseCaseTests

import com.valijonboy.data.datasource.entity.Contact
import com.valijonboy.data.repository.ContactRepository
import com.valijonboy.domain.usecase.ContactUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Incubating
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {

    @Incubating
    interface ResourcesPackagingOptions

    @Mock
    private lateinit var mockRepository: ContactRepository

    private lateinit var insertContactUseCase: ContactUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        insertContactUseCase = ContactUseCase(mockRepository)
    }

    @Test
    fun testInsertContactUseCase() = runBlocking {
        // Arrange
        val contact = Contact(1, "John Doe")

        // Act
        insertContactUseCase.insertContact(contact)

        // Assert
        // Verify that the insertContact function of the repository was called with the correct contact
        Mockito.verify(mockRepository).insertContact(contact)
    }
}