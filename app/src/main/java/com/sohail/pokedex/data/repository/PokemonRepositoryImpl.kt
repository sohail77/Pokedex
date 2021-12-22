package com.sohail.pokedex.data.repository

import android.net.Uri
import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.data.network.ApiService
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PokemonRepository {

    override suspend fun getPokemons(): List<Pokemon> {
        val pokemonData = apiService.getPokemons()
        val result = mutableListOf<Pokemon>()
        pokemonData.results.forEach {
            val lastPath = Uri.parse(it.url).lastPathSegment?.toInt()
            result.add(getPokemonDetails(lastPath ?: 1))
        }
        return result
    }

    private suspend fun getPokemonDetails(id: Int) : Pokemon {
        val temp = apiService.getPokemonDetails(id)
        return Pokemon(
            id = temp.id,
            name = temp.name,
            imageUrl = "https://cdn.traction.one/pokedex/pokemon/${temp.id}.png"
        )
    }
}