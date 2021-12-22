package com.sohail.pokedex.di

import com.sohail.pokedex.data.network.ApiService
import com.sohail.pokedex.data.repository.PokemonRepository
import com.sohail.pokedex.data.repository.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PokemonRepositoryModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val baseUrl = "https://pokeapi.co/api/v2/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Provides
    fun providePokemonRepository(apiService: ApiService): PokemonRepository =
        PokemonRepositoryImpl(apiService)
}