package com.valijonboy.data.datasource.persistent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valijonboy.data.datasource.dao.ContactDao
import com.valijonboy.data.datasource.entity.Contact

@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)
internal abstract class ContactDatabase : RoomDatabase() {

    abstract val contactDao: ContactDao

    companion object {
      private const val DATABASE_NAME: String = "contact_database"

      fun create(context: Context) : ContactDatabase =
          Room.databaseBuilder(context, ContactDatabase::class.java, DATABASE_NAME)
              .fallbackToDestructiveMigration()
              .build()

    }
}