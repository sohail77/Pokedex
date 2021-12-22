package com.sohail.pokedex.data.network

import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.data.models.PokemonApiData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon/")
    suspend fun getPokemons(): PokemonApiData

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path(value = "id") id: Int): Pokemon
}

object RetrofitBuilder {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}