package com.example.pokedex.model.models

import androidx.compose.ui.graphics.Color

data class PokemonList (
    val count: Int,
    val previous: String?,
    val next: String?,
    val results: List<Pokemon>
)


data class Pokemon(
    val name: String,
    val id: String,
)


data class PokemonDetail(
    val id: Int,
    val name: String,
    val stats: List<PokemonStats>,
    val types: List<PokemonType>,
)


data class PokemonStats(
    val baseStat: Int,
    val stat: PokemonStat
)


data class PokemonStat(
    val name: String,
    val url: String,
)

data class PokemonType(
    val name: String,
    val id: String,
    val color: Color,
)