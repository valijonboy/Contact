package com.valijonboy.data.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.valijonboy.data.datasource.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(contact: Contact)

    @Update
    abstract suspend fun update(contact: Contact)

    @Delete
    abstract suspend fun delete(contact: Contact)

    @Query("SELECT * from contacts WHERE id = :id")
    abstract fun getContact(id: Int): Flow<Contact>

    @Query("SELECT * from contacts ORDER BY name ASC")
    abstract fun getAllContacts(): Flow<List<Contact>>
}