package com.example.pokedex.model.service

import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonList
import com.example.pokedex.model.models.PokemonType

interface PokedexService {
    suspend fun getPokemonList(limit: Int = 20): PokemonList
    suspend fun getPokemon(name: String): PokemonDetail
    suspend fun getPokemonTypeList(): List<PokemonType>
    suspend fun getPokemonListByType(id: String): List<Pokemon>
}