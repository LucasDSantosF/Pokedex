package com.example.pokedex.model.models

sealed interface ActionsType {
    data object LoadList : ActionsType

    data object LoadMoreList : ActionsType

    data object ByNameOrId : ActionsType

    data class ByType(val id: String) : ActionsType

    data class LoadDetail(val id: String) : ActionsType
}