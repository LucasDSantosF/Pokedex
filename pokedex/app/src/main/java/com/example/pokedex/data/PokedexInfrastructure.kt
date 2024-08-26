package com.example.pokedex.data

import com.example.pokedex.data.gateway.Gateway
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonList
import com.example.pokedex.model.service.PokedexService

class PokedexInfrastructure(
    private val gateway: Gateway,
) : PokedexService {
    override suspend fun getPokemonList(limit: Int): PokemonList {
        val response = gateway.getPokemonList(limit)
        return response.toDomain()
    }

    override suspend fun getPokemon(name: String): PokemonDetail {
        val response = gateway.getPokemon(name = name)
        return response.toDomain()
    }
}