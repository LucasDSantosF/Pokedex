package com.example.pokedex.data.gateway

import com.example.pokedex.data.response.PokemonDetailResponse
import com.example.pokedex.data.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Gateway {
    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int) : PokemonListResponse

    @GET("pokemon/{id}")
    fun getPokemon(id: Int) : PokemonDetailResponse
}