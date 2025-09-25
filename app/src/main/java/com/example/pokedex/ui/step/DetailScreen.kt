package com.example.pokedex.ui.step

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.ui.PokedexStrings
import com.example.pokedex.ui.step.components.EmptyScreen
import com.example.pokedex.ui.step.components.LoadingContent
import com.example.pokedex.ui.step.components.detail.PokemonImage
import com.example.pokedex.ui.step.components.detail.PokemonInfo
import com.example.pokedex.ui.step.components.detail.ToolbarDetails

data class DetailScreen(val stateData: PokedexStateData, val id: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<PokedexFlowModel>()
        val state by screenModel.state.observeAsState()
        val strings = remember { PokedexStrings() }

        BackHandler {
            navigator.replaceAll(ListScreen())
        }

        when (val result = state) {
            PokedexState.Loading -> LoadingContent()
            is PokedexState.Result ->
                Column {
                    PokemonDetailBody(
                        pokemon = result.state.details,
                        strings = strings,
                        msg = result.state.errorMsg,
                        image = remember { { id -> screenModel.getImageURL(id) } },
                        imageHome = remember { { id -> screenModel.getImageHomeURL(id) } },
                        backAction = remember { { navigator.replaceAll(ListScreen()) } },
                        onClickBadge = remember {
                            { id ->
                                navigator.replaceAll(ListScreen(id))
                            }
                        },
                        onClick = remember { { screenModel.reloadAction(result.state) } },
                    )
                }

            else -> {}
        }

        LaunchedEffect(key1 = screenModel) {
            screenModel.getDetail(stateData, id)
        }
    }

    @Composable
    private fun ColumnScope.PokemonDetailBody(
        pokemon: PokemonDetail,
        msg: String?,
        strings: PokedexStrings,
        image: (String) -> String,
        imageHome: (String) -> String,
        backAction: () -> Unit,
        onClick: () -> Unit,
        onClickBadge: (String) -> Unit
    ) {
        ToolbarDetails(
            name = pokemon.name,
            backAction = backAction,
            backGroundColor = pokemon.color,
        )
        if (msg != null)
            EmptyScreen(
                msg = msg,
                strings = strings,
                image = image,
                onClickAction = onClick,
            )
        else
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = pokemon.color)
                    .weight(1f)
            ) {
                LazyColumn {
                    item {
                        PokemonImage(
                            pokemon = pokemon,
                            image = image,
                            imageHome = imageHome,
                        )
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            colors =
                                CardDefaults.cardColors()
                                    .copy(containerColor = MaterialTheme.colorScheme.primary),
                        ) {
                            PokemonInfo(
                                pokemon = pokemon,
                                strings = strings.details,
                                onClickBadge = onClickBadge,
                            )
                        }
                    }
                }
            }
    }
}
