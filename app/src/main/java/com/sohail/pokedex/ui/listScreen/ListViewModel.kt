package com.sohail.pokedex.ui.listScreen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.data.repository.PokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    val pokemonPagingSource: PokemonPagingSource
) : ViewModel() {

    fun getPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(PagingConfig(10)) { pokemonPagingSource }.flow
    }
}