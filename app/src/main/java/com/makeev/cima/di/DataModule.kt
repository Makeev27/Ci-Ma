package com.makeev.cima.di

import com.makeev.cima.data.TestMovieRepositoryImpl
import com.makeev.cima.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindMovieRepository(
        impl: TestMovieRepositoryImpl
    ) : MovieRepository

}