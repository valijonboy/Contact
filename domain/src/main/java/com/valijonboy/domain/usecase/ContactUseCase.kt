package com.valijonboy.domain.usecase

import com.valijonboy.data.datasource.entity.Contact
import com.valijonboy.data.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactUseCase @Inject constructor(
    private val repository: ContactRepository
) {

    suspend fun insertContact(contact: Contact) = repository.insertContact(contact)

    suspend fun deleteContact(contact: Contact) = repository.deleteContact(contact)

    suspend fun updateContact(contact: Contact) = repository.updateContact(contact)

    fun getContact(id: Int): Flow<Contact?> = repository.getContact(id).flowOn(Dispatchers.IO)

    fun getAllContacts(): Flow<List<Contact>> = repository.getAllContacts().flowOn(Dispatchers.IO)

}