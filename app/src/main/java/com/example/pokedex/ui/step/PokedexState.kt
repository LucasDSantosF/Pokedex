package com.example.pokedex.ui.step

import androidx.compose.ui.graphics.Color
import com.example.pokedex.model.models.ActionsType
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonType

sealed class PokedexState {
    data object Loading : PokedexState()

    data class Result(val state: PokedexStateData) : PokedexState()
}

data class PokedexStateData(
    val list: List<Pokemon> = emptyList(),
    val typeList: List<PokemonType> = emptyList(),
    val inputText: String = "",
    val details: PokemonDetail = PokemonDetail(
        id = "",
        name = "",
        number = "",
        color = Color.Gray,
        stats = emptyList(),
        types = emptyList(),
    ),
    val selectedType: String? = null,
    val limit: Int = 20,
    val errorMsg: String? = null,
    val actionsType: ActionsType = ActionsType.LoadList,
)