package com.example.pokedex.ui.step

import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.model.service.PokedexService
import com.example.pokedex.ui.PokedexStrings
import kotlinx.coroutines.launch

data class PokedexState(
    val list: List<Pokemon> = emptyList(),
    val typeList: List<PokemonType> = emptyList(),
    val inputText: String = "",
    val details: PokemonDetail = PokemonDetail(
        id = "",
        name = "",
        number = "",
        color = Color.Gray,
        stats = emptyList(),
        types = emptyList(),
    ),
    val selectedType: String? = null,
    val limit: Int = 20,
    val errorMsg: String? = null,
)

class PokedexFlowModel(
    private val service: PokedexService,
    private val strings: PokedexStrings,
) : StateScreenModel<PokedexState>(PokedexState()) {

    fun getList() {
        screenModelScope.launch {
            runCatching {
                service.getPokemonList() to service.getPokemonTypeList()
            }.onSuccess { result ->
                val (pokemonList, typeList) = result

                mutableState.value = state.value.copy(
                    list = pokemonList.results,
                    typeList = typeList,
                )
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                )
            }
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
                        id = result.id
                    )
                ))
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                )
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
            }.onFailure {
                    mutableState.value = state.value.copy(
                        errorMsg = strings.errorMsg,
                    )
                }
        }
    }

    fun getDetail(id: String) {
        screenModelScope.launch {
            runCatching {
                service.getPokemon(id)
            }.onSuccess { pokemon ->
                mutableState.value = state.value.copy(
                    details = pokemon,
                )
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                )
            }
        }
    }

    fun updateSelectedType(id: String?, comingToDetail: Boolean = false) {
        if (!comingToDetail && mutableState.value.selectedType == id) {
            mutableState.value = state.value.copy(selectedType = null)
            getList()
        } else
            mutableState.value = state.value.copy(selectedType = id)
    }

    fun loadMoreList() {
        screenModelScope.launch {
            val limit = mutableState.value.limit+20
            runCatching {
                service.getPokemonList(limit) to service.getPokemonTypeList()
            }.onSuccess { result ->
                val (pokemonList, typeList) = result

                mutableState.value = state.value.copy(
                    list = pokemonList.results,
                    typeList = typeList,
                    limit = limit,
                )
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                )
            }
        }
    }

    fun getImageURL(id: String) =
        "$IMAGE_URL$id.png"

    fun getImageHomeURL(id: String) =
        "$IMAGE_HOME_URL$id.png"

    private companion object {
        const val IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        const val IMAGE_HOME_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/"
    }
}