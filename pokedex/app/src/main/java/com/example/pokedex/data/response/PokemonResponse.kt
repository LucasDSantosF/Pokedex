package com.example.pokedex.data.response

import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonList
import com.example.pokedex.model.models.PokemonStat
import com.example.pokedex.model.models.PokemonStats
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.model.models.TypeColors
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
                id = pokemon.url.getId(POKEMON_URL),
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
    val types: List<PokemonTypeSlotResponse>,
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
        types = types.map { typeSlot ->
            val id = typeSlot.type.url.getId(POKEMON_TYPE_URL)
            PokemonType(
                name = typeSlot.type.name,
                id = id,
                color = id.getColor()
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

@Serializable
data class PokemonTypeListResponse(
    val count: Int,
    val previous: String?,
    val next: String?,
    val results: List<PokemonTypeResponse>
) {
    fun toDomain() = results.map { type ->
        val id = type.url.getId(POKEMON_TYPE_URL)
        PokemonType(
            name = type.name,
            id = id,
            color = id.getColor()
        )
    }
}

@Serializable
data class PokemonTypeSlotResponse(
    val type: PokemonTypeResponse,
)

@Serializable
data class PokemonTypeResponse(
    val name: String,
    val url: String,
)

@Serializable
data class PokemonListByTypeResponse(
    val pokemon: List<PokemonSlot>
) {
    fun toDomain() = pokemon.map { pokemon ->
        Pokemon(
            name = pokemon.pokemon.name,
            id = pokemon.pokemon.url.getId(POKEMON_URL),
        )
    }
}

@Serializable
data class PokemonSlot(
    val pokemon: PokemonResponse
)

private fun String.getId(oldValue: String) =
    this.replace(oldValue = oldValue, newValue = "")
        .replace("/", "")

private fun String.getColor() =
    (TypeColors.entries.firstOrNull { typeColors ->
        typeColors.id == this
    } ?: TypeColors.Unknown).color

private const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/"
private const val POKEMON_TYPE_URL = "https://pokeapi.co/api/v2/type/"