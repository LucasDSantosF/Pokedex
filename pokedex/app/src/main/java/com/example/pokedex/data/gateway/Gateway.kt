package com.example.pokedex.data.gateway

import com.example.pokedex.data.response.PokemonDetailResponse
import com.example.pokedex.data.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Gateway {
    @GET("pokemon/")
    suspend fun getPokemonList(@Query("limit") limit: Int) : PokemonListResponse

    @GET("pokemon/{name}/")
    suspend fun getPokemon(@Path("name") name: String) : PokemonDetailResponse
}