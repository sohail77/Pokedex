package com.sohail.pokedex.data.models

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String
)


data class PokemonApiData(
    val results: List<PokemonOverview>
)

data class PokemonOverview(
    val name: String,
    val url: String
)
