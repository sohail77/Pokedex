package com.sohail.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.ui.listScreen.ListViewModel
import com.sohail.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pokemonViewModel : ListViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonList(pokemonViewModel.getPokemons())
                }
            }
        }
    }
}

private const val CELL_COUNT = 2

@OptIn(ExperimentalFoundationApi::class)
private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(CELL_COUNT) }

@ExperimentalFoundationApi
@Composable
fun PokemonList(list: Flow<PagingData<Pokemon>>) {
    val lazyPokemonItems = list.collectAsLazyPagingItems()
    LazyVerticalGrid(
        cells = GridCells.Fixed(CELL_COUNT),
        modifier = Modifier.padding(8.dp)
    ) {
        items(lazyPokemonItems.itemCount) { index ->
            lazyPokemonItems[index]?.let {
                PokemonTile(pokemon = it)
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
fun PokemonTile(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
        PokemonList(flowOf(PagingData.from(listOf(Pokemon(1, "bulbasaur", ""),Pokemon(2, "bulbasaur", ""),Pokemon(3, "bulbasaur", "")))))
    }
}