package com.example.pokedex.ui.step

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.model.service.PokedexService
import kotlinx.coroutines.launch

data class PokedexState(
    val list: List<Pokemon> = emptyList(),
    val typeList: List<PokemonType> = emptyList(),
    val inputText: String = "",
    val details: PokemonDetail = PokemonDetail(
        id = 0,
        name = "",
        stats = emptyList(),
        types = emptyList(),
    ),
    val selectedType: String? = null
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

    fun getDetail(id: String) {
        screenModelScope.launch {
            val pokemon = service.getPokemon(id)

            mutableState.value = state.value.copy(
                details = pokemon,
            )
        }
    }

    fun updateSelectedType(id: String?, comingToDetail: Boolean = false) {
        if (!comingToDetail && mutableState.value.selectedType == id) {
            mutableState.value = state.value.copy(selectedType = null)
            getList()
        } else
            mutableState.value = state.value.copy(selectedType = id)
    }


    fun getImageURL(id: String) =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}