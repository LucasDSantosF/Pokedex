package com.example.pokedex.ui.step

import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.livedata.LiveScreenModel
import com.example.pokedex.model.models.ActionsType
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.service.PokedexService
import com.example.pokedex.ui.PokedexStrings
import kotlinx.coroutines.launch

class PokedexFlowModel(
    private val service: PokedexService,
) : LiveScreenModel<PokedexState>(PokedexState.Loading) {
    private val strings = PokedexStrings()

    fun getList(preSelectedId: String? = null) {
        screenModelScope.launch {
            runCatching {
                getPokemonList(preSelectedId) to service.getPokemonTypeList()
            }.onSuccess { result ->
                val (pokemonList, typeList) = result

                mutableState.postValue(
                    PokedexState.Result(
                        PokedexStateData(
                            list = pokemonList,
                            typeList = typeList,
                            errorMsg = null,
                            selectedType = preSelectedId
                        )
                    )
                )
            }.onFailure {
                mutableState.postValue(
                    PokedexState.Result(
                        PokedexStateData(
                            errorMsg = strings.errorMsg,
                            actionsType = ActionsType.LoadList,
                        )
                    )
                )
            }
        }
    }

    private suspend fun getPokemonList(preSelectedId: String? = null) : List<Pokemon> =
        if (preSelectedId != null)
            service.getPokemonListByType(preSelectedId)
        else service.getPokemonList()

    fun getPokemonByNameOrId(
        inputText: String,
        stateData: PokedexStateData,
    ) {
        screenModelScope.launch {
            runCatching {
                service.getPokemon(inputText)
            }.onSuccess { result ->
                mutableState.value = PokedexState.Result(
                    stateData.copy(
                        list = listOf(
                            Pokemon(
                                name = result.name,
                                id = result.id,
                            )
                        ),
                        errorMsg = null,
                    )
                )
            }.onFailure {
                mutableState.value = PokedexState.Result(
                    stateData.copy(
                        errorMsg = strings.errorMsg,
                        actionsType = ActionsType.ByNameOrId
                    )
                )
            }
        }
    }

    fun updateInputText(stateData: PokedexStateData, inputText: String) {
        mutableState.value = PokedexState.Result(
            stateData.copy(inputText = inputText)
        )
    }

    fun getPokemonByType(
        stateData: PokedexStateData,
        id: String,
    ) {
        screenModelScope.launch {
            runCatching {
                service.getPokemonListByType(id)
            }.onSuccess { result ->
                mutableState.value =
                    PokedexState.Result(
                        stateData.copy(selectedType = id, list = result, errorMsg = null)
                    )
            }.onFailure { exception ->
                print(exception)
                mutableState.value = PokedexState.Result(
                    stateData.copy(
                        errorMsg = strings.errorMsg,
                        actionsType = ActionsType.ByType(id),
                    )
                )
            }
        }
    }

    fun getDetail(stateData: PokedexStateData, id: String) {
        screenModelScope.launch {
            runCatching {
                service.getPokemon(id)
            }.onSuccess { pokemon ->
                mutableState.postValue(
                    PokedexState.Result(
                        stateData.copy(
                            details = pokemon,
                            errorMsg = null,
                        )
                    )
                )
            }.onFailure {
                mutableState.postValue(
                    PokedexState.Result(
                        stateData.copy(
                            errorMsg = strings.errorMsg,
                            actionsType = ActionsType.LoadDetail(id),
                        )
                    )
                )
            }
        }
    }

    fun updateSelectedType(
        id: String,
        preSelectedType: String?,
        comingToDetail: Boolean = false,
        stateData: PokedexStateData,
    ) {
        if (!comingToDetail && preSelectedType == id) {
            mutableState.value = PokedexState.Result(PokedexStateData())
            getList()
        } else getPokemonByType(stateData, id)
    }

    fun loadMoreList(stateData: PokedexStateData) {
        screenModelScope.launch {
            val limit = stateData.limit + 20
            runCatching {
                service.getPokemonList(limit) to service.getPokemonTypeList()
            }.onSuccess { result ->
                val (pokemonList, typeList) = result

                mutableState.value = PokedexState.Result(
                    stateData.copy(
                        list = pokemonList,
                        typeList = typeList,
                        limit = limit,
                        errorMsg = null,
                    )
                )
            }.onFailure {
                mutableState.value = PokedexState.Result(
                    stateData.copy(
                        errorMsg = strings.errorMsg,
                        actionsType = ActionsType.LoadMoreList,
                    )
                )
            }
        }
    }

    fun reloadAction(stateData: PokedexStateData) {
        when (val action = stateData.actionsType) {
            ActionsType.ByNameOrId ->
                getPokemonByNameOrId(stateData.inputText, stateData)

            is ActionsType.ByType ->
                getPokemonByType(stateData, action.id)

            is ActionsType.LoadDetail ->
                getDetail(stateData, action.id)

            ActionsType.LoadList ->
                getList()

            ActionsType.LoadMoreList ->
                loadMoreList(stateData)
        }
    }

    fun getImageURL(id: String) =
        "$IMAGE_HOME_URL$id.png"

    fun getImageHomeURL(id: String) =
        "$IMAGE_URL$id.png"

    companion object {
        const val IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        const val IMAGE_HOME_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/"
    }
}