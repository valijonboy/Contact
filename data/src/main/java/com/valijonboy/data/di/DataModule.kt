package com.valijonboy.data.di

import android.content.Context
import com.valijonboy.data.datasource.dao.ContactDao
import com.valijonboy.data.datasource.persistent.ContactDatabase
import com.valijonboy.data.repository.ContactRepository
import com.valijonboy.data.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    fun provideDatabase(context: Context) : ContactDatabase {
        return ContactDatabase.create(context)
    }

    @Provides
    fun provideDao(database: ContactDatabase) : ContactDao {
        return database.contactDao
    }

    @Provides
    fun provideRepository(dao: ContactDao) : ContactRepository {
        return ContactRepositoryImpl(dao)
    }
}