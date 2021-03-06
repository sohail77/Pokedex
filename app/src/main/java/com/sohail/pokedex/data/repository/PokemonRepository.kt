package com.sohail.pokedex.data.repository

import com.sohail.pokedex.data.models.Pokemon

interface PokemonRepository {

    suspend fun getPokemons(offset: Int): List<Pokemon>
}