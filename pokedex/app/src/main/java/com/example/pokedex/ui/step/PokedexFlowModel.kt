package com.example.pokedex.ui.step

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.model.service.PokedexService
import kotlinx.coroutines.launch

data class PokedexState(
    val list: List<Pokemon> = emptyList(),
    val typeList: List<PokemonType> = emptyList(),
    val inputText: String = "",
)

class PokedexFlowModel(
    private val service: PokedexService,
) : StateScreenModel<PokedexState>(PokedexState()) {

    fun getList() {
        screenModelScope.launch {
            val pokemonList = service.getPokemonList()
            val typeList = service.getPokemonTypeList()
            mutableState.value = state.value.copy(
                list = pokemonList.results,
                typeList = typeList,
            )
        }
    }

    fun getPokemonByNameOrId() {
        screenModelScope.launch {
            runCatching {
                service.getPokemon(mutableState.value.inputText)
            }.onSuccess { result ->
                mutableState.value = state.value.copy(list = listOf(
                    Pokemon(
                        name = result.name,
                        id = result.id.toString()
                    )
                ))
            }
        }
    }

    fun updateInputText(inputText: String) {
        mutableState.value = state.value.copy(inputText = inputText)
    }

    fun getPokemonByType(id: String) {
        screenModelScope.launch {
            runCatching {
                service.getPokemonListByType(id)
            }.onSuccess { result ->
                mutableState.value = state.value.copy(list = result)
            }
        }
    }

    fun getImageURL(id: String) =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}