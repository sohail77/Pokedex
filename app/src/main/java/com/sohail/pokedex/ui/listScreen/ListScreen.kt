package com.sohail.pokedex.ui.listScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.navigation.Screen
import com.sohail.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private const val CELL_COUNT = 2

@OptIn(ExperimentalFoundationApi::class)
private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(CELL_COUNT) }


@ExperimentalFoundationApi
@Composable
fun PokemonListScreen(viewModel: ListViewModel, navController: NavController) {

    PokemonList(list = viewModel.getPokemons())  {
        navController.navigate(Screen.DetailScreen.createRoute(it))
    }
}


@ExperimentalFoundationApi
@Composable
fun PokemonList(list: Flow<PagingData<Pokemon>>, onPokemonClicked: (Int) -> Unit = {}) {
    val lazyPokemonItems = list.collectAsLazyPagingItems()
    LazyVerticalGrid(
        cells = GridCells.Fixed(CELL_COUNT),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        items(lazyPokemonItems.itemCount) { index ->
            lazyPokemonItems[index]?.let {
                PokemonTile(pokemon = it, itemClicked = { onPokemonClicked(it) })
            }
        }
        renderLoading(lazyPokemonItems.loadState)
    }
}

@ExperimentalFoundationApi
private fun LazyGridScope.renderLoading(loadState: CombinedLoadStates) {
    when {
        loadState.refresh is LoadState.Loading -> {
            item(span = span) { LoadingView(modifier = Modifier.fillMaxHeight()) }
        }
        loadState.append is LoadState.Loading -> {
            item(span = span) { LoadingView(modifier = Modifier.padding(10.dp)) }
        }
        else -> return
    }
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PokemonTile(modifier: Modifier = Modifier, pokemon: Pokemon, itemClicked: (Int) -> Unit = {} ) {
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .clickable { itemClicked(pokemon.id) },
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Image(
                painter = rememberImagePainter(data = pokemon.imageUrl),
                contentDescription = "pokemon Image",
                modifier = Modifier
                    .size(150.dp)
            )
            Text(
                text = pokemon.name,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokedexTheme {
        PokemonList(
            flowOf(
                PagingData.from(listOf(
                    Pokemon(1, "bulbasaur", "https://cdn.traction.one/pokedex/pokemon/1.png"),
                    Pokemon(2, "bulbasaur", "https://cdn.traction.one/pokedex/pokemon/1.png"),
                    Pokemon(3, "bulbasaur", "https://cdn.traction.one/pokedex/pokemon/1.png")
                )))
        )
    }
}