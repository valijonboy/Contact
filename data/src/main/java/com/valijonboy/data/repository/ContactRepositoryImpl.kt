package com.valijonboy.data.repository

import com.valijonboy.data.datasource.dao.ContactDao
import com.valijonboy.data.datasource.entity.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val dao: ContactDao
) : ContactRepository {

    override suspend fun insertContact(contact: Contact) = dao.insert(contact)

    override suspend fun deleteContact(contact: Contact) = dao.delete(contact)

    override suspend fun updateContact(contact: Contact) = dao.update(contact)

    override fun getContact(id: Int): Flow<Contact?> {
       return dao.getContact(id)
    }

    override fun getAllContacts(): Flow<List<Contact>> = dao.getAllContacts()


}

