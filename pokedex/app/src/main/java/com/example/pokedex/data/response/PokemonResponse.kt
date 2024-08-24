package com.example.pokedex.data.response

import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonList
import com.example.pokedex.model.models.PokemonStat
import com.example.pokedex.model.models.PokemonStats
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val count: Int,
    val previous: String?,
    val next: String?,
    val results: List<PokemonResponse>
) {
    fun toDomain() = PokemonList(
        count = count,
        previous = previous,
        next = next,
        results = results.map { pokemon ->
            Pokemon(
                name = pokemon.name,
                url = pokemon.url,
            )
        },
    )
}

@Serializable
data class PokemonResponse(
    val name: String,
    val url: String,
)

@Serializable
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val stats: List<PokemonStatsResponse>,
) {
    fun toDomain() = PokemonDetail(
        id = id,
        name = name,
        stats = stats.map { stat ->
            PokemonStats(
                baseStat = stat.baseStat,
                stat = PokemonStat(
                    name = stat.stat.name,
                    url = stat.stat.url,
                ),
            )
        },
    )
}

@Serializable
data class PokemonStatsResponse(
    @SerialName("base_stat") val baseStat: Int,
    val stat: PokemonStatResponse,
)

@Serializable
data class PokemonStatResponse(
    val name: String,
    val url: String,
)