package com.bangkit.jajananapp_bintang.di

import com.bangkit.jajananapp_bintang.data.JajananRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJajananRepository(): JajananRepository {
        return JajananRepository()
    }
}