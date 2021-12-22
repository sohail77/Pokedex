package com.sohail.pokedex.ui.listScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    val pokemonRepository: PokemonRepository
) : ViewModel() {

    var pokemonList = mutableStateListOf<Pokemon>()
        private set

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonList.addAll(pokemonRepository.getPokemons())
        }
    }
}