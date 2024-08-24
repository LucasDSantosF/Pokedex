package com.example.pokedex.model.service

import retrofit2.http.Query

interface PokedexService {
    suspend fun getPokedexList( offset: Int, limit: Int)
}