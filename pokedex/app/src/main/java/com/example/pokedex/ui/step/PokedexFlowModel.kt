package com.example.pokedex.ui.step

import cafe.adriel.voyager.core.model.ScreenModel
import com.example.pokedex.model.service.PokedexService
import kotlinx.coroutines.coroutineScope

class PokedexFlowModel(
    private val service: PokedexService,
) : ScreenModel {


    suspend fun getList() {
        coroutineScope() {
            val result = service.getPokemonList()
            result
        }
    }
}