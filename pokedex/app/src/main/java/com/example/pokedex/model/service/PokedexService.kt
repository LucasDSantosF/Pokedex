package com.example.pokedex.model.service

import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonList
import retrofit2.http.Query

interface PokedexService {
    suspend fun getPokemonList(limit: Int = 20): PokemonList
    suspend fun getPokemon(id: Int): PokemonDetail
}