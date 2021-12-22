package com.sohail.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.sohail.pokedex.data.models.Pokemon
import com.sohail.pokedex.ui.listScreen.ListViewModel
import com.sohail.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val pokemonViewModel : ListViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonViewModel.getPokemons()
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonList(pokemonViewModel.pokemonList)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PokemonList(list: List<Pokemon>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp)) {
        items(items = list) { pokemon ->
            PokemonTile(pokemon = pokemon)
        }
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
        PokemonList(listOf(Pokemon(1, "bulbasaur", ""),Pokemon(2, "bulbasaur", ""),Pokemon(3, "bulbasaur", "")))
    }
}