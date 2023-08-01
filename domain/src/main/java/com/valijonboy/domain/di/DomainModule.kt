package com.valijonboy.domain.di

import com.valijonboy.data.repository.ContactRepository
import com.valijonboy.domain.usecase.ContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideUseCase(repository: ContactRepository) : ContactUseCase {
        return ContactUseCase(repository)
    }
}