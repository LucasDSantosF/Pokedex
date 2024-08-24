package com.example.pokedex.data.gatewayBuilder

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitBuilder {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private val contentType = MediaType.get("application/json")

    private val json = Json{
        ignoreUnknownKeys = true
    }

    operator fun invoke(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    inline fun <reified T> Retrofit.buildGateway(): T = create(T::class.java)
}