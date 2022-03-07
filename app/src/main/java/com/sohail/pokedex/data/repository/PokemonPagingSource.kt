package com.sohail.pokedex.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sohail.pokedex.data.models.Pokemon
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val nextPage = params.key ?: 0
        val pokemonResponse = pokemonRepository.getPokemons(nextPage)

        return LoadResult.Page(
            data = pokemonResponse,
            prevKey = if(nextPage == 0) null
        else nextPage - 20,
            nextKey = nextPage + 20
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }
}