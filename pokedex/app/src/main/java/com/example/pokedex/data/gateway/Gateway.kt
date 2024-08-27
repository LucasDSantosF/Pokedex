package com.example.pokedex.data.gateway

import com.example.pokedex.data.response.PokemonDetailResponse
import com.example.pokedex.data.response.PokemonListByTypeResponse
import com.example.pokedex.data.response.PokemonListResponse
import com.example.pokedex.data.response.PokemonTypeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Gateway {
    @GET("pokemon/")
    suspend fun getPokemonList(@Query("limit") limit: Int) : PokemonListResponse

    @GET("pokemon/{name}/")
    suspend fun getPokemon(@Path("name") name: String) : PokemonDetailResponse

    @GET("type/")
    suspend fun getPokemonTypeList() : PokemonTypeListResponse

    @GET("type/{id}/")
    suspend fun getPokemonListByType(@Path("id") name: String) : PokemonListByTypeResponse
}