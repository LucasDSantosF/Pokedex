package com.example.pokedex.ui

data class PokedexStrings(
    val list: PokedexListStrings = PokedexListStrings(),
    val details: PokedexDetailStrings = PokedexDetailStrings(),
    val errorMsg: String = "Ops.. Algo de errado tente novamente",
)

data class PokedexListStrings(
    val placeholder: String = "Buscar por nome ou numero",
    val title: String = "Pokédex",
    val description: String = "Procure seu Pokémon",
)

data class PokedexDetailStrings(
    val statLabel: String = "Estatísticas",
    val typeLabel: String = "Tipos",
)
