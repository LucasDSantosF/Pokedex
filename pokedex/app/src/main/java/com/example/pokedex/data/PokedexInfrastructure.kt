package com.example.pokedex.data

import com.example.pokedex.data.gateway.Gateway
import com.example.pokedex.model.service.PokedexService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PokedexInfrastructure(
    private val gateway: Gateway,
) : PokedexService {
    override suspend fun getPokedexList(offset: Int, limit: Int) {
        gateway.getPokedexList(offset, limit)
    }
}