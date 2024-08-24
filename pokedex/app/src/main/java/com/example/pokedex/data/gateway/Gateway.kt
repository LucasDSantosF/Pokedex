package com.example.pokedex.data.gateway

import retrofit2.http.GET
import retrofit2.http.Query

interface Gateway {
    @GET("pokedex/")
    fun getPokedexList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    )
}