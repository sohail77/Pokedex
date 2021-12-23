package com.sohail.pokedex.data.network

import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.data.models.PokemonApiData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemons(@Query("offset") offset: Int): PokemonApiData

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path(value = "id") id: Int): Pokemon
}