package com.example.pokedex

import com.example.pokedex.PokedexTestData.pokemonDetail
import com.example.pokedex.PokedexTestData.pokemonList
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonList
import com.example.pokedex.model.models.PokemonStat
import com.example.pokedex.model.models.PokemonStats
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.model.models.TypeColors
import com.example.pokedex.model.service.PokedexService

class FakePokedexService : PokedexService {
    var pokemonListResult: PokemonList = pokemonList()
    var pokemonDetailResult: PokemonDetail = pokemonDetail()
    var pokemonTypeResult: List<PokemonType> = pokemonDetail().types
    var pokemonListByTypeResult: List<Pokemon> = pokemonList().results

    override suspend fun getPokemonList(limit: Int): PokemonList =
        pokemonListResult

    override suspend fun getPokemon(name: String): PokemonDetail =
        pokemonDetailResult

    override suspend fun getPokemonTypeList(): List<PokemonType> =
        pokemonTypeResult

    override suspend fun getPokemonListByType(id: String): List<Pokemon> =
        pokemonListByTypeResult
}

internal object PokedexTestData {

    fun pokemonList() = PokemonList(
        count = 0,
        previous = null,
        next = null,
        results = listOf(
            Pokemon(
                name = "pigetto",
                id = "17",
            ),
            Pokemon(
                name = "pikachu",
                id = "18",
            ),
        )
    )

    fun pokemonDetail() = PokemonDetail(
        id = "17",
        name = "pigetto",
        number = "017",
        color = TypeColors.Flying.color,
        stats = listOf(
            PokemonStats(
                baseStat = "89",
                stat = PokemonStat(
                    name = "Ataque",
                    url = "",
                )
            ),
            PokemonStats(
                baseStat = "80",
                stat = PokemonStat(
                    name = "Defeca",
                    url = "",
                )
            )
        ),
        types = listOf(
            PokemonType(
                name = "flying",
                id = "3",
                color = TypeColors.Flying.color,
            )
        ),
    )
}