package com.example.pokedex.ui.step

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel

class ListScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<PokedexFlowModel>()

        LaunchedEffect(key1 = screenModel) {
            screenModel.getList()
        }
    }
}