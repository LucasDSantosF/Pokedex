package com.example.pokedex.model.models

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
)