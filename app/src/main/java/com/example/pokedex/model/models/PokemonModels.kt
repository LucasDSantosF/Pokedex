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
    val id: String,
    val name: String,
    val number: String,
    val color: Color,
    val stats: List<PokemonStats>,
    val types: List<PokemonType>,
)


data class PokemonStats(
    val baseStat: String,
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