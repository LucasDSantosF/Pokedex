package com.example.pokedex.ui.step

import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.pokedex.model.models.ActionsType
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
    val actionsType: ActionsType = ActionsType.LoadList,
)

class PokedexFlowModel(
    private val service: PokedexService,
) : StateScreenModel<PokedexState>(PokedexState()) {
    private val strings = PokedexStrings()

    fun getList() {
        screenModelScope.launch {
            runCatching {
                service.getPokemonList() to service.getPokemonTypeList()
            }.onSuccess { result ->
                val (pokemonList, typeList) = result

                mutableState.value = state.value.copy(
                    list = pokemonList.results,
                    typeList = typeList,
                    errorMsg = null,
                )
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                    actionsType = ActionsType.LoadList,
                )
            }
        }
    }

    fun getPokemonByNameOrId() {
        screenModelScope.launch {
            runCatching {
                service.getPokemon(mutableState.value.inputText)
            }.onSuccess { result ->
                mutableState.value = state.value.copy(
                    list = listOf(
                        Pokemon(
                            name = result.name,
                            id = result.id,
                        )
                    ),
                    errorMsg = null,
                )
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                    actionsType = ActionsType.ByNameOrId
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
                mutableState.value = state.value.copy(list = result, errorMsg = null)
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                    actionsType = ActionsType.ByType(id),
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
                    errorMsg = null,
                )
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                    actionsType = ActionsType.LoadDetail(id),
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
            val limit = mutableState.value.limit + 20
            runCatching {
                service.getPokemonList(limit) to service.getPokemonTypeList()
            }.onSuccess { result ->
                val (pokemonList, typeList) = result

                mutableState.value = state.value.copy(
                    list = pokemonList.results,
                    typeList = typeList,
                    limit = limit,
                    errorMsg = null,
                )
            }.onFailure {
                mutableState.value = state.value.copy(
                    errorMsg = strings.errorMsg,
                    actionsType = ActionsType.LoadMoreList,
                )
            }
        }
    }

    fun reloadAction() {
        when (val action = mutableState.value.actionsType) {
            ActionsType.ByNameOrId ->
                getPokemonByNameOrId()

            is ActionsType.ByType ->
                getPokemonByType(action.id)

            is ActionsType.LoadDetail ->
                getDetail(action.id)

            ActionsType.LoadList ->
                getList()

            ActionsType.LoadMoreList ->
                loadMoreList()
        }
    }

    fun getImageURL(id: String) =
        "$IMAGE_URL$id.png"

    fun getImageHomeURL(id: String) =
        "$IMAGE_HOME_URL$id.png"

    companion object {
        const val IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        const val IMAGE_HOME_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/"
    }
}