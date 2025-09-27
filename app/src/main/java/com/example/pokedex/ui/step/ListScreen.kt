package com.example.pokedex.ui.step

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.pokedex.ui.PokedexStrings
import com.example.pokedex.ui.step.components.EmptyScreen
import com.example.pokedex.ui.step.components.LoadingContent
import com.example.pokedex.ui.step.components.list.Header
import com.example.pokedex.ui.step.components.list.PokemonCard
import com.example.pokedex.ui.step.components.list.SearchInputAndTypesBadges
import com.example.pokedex.ui.util.EndlessLazyColumn

class ListScreen(private val preSelectedId: String? = null) : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<PokedexFlowModel>()
        val state by screenModel.state.observeAsState()
        val strings = remember { PokedexStrings() }

        when (val result = state) {
            PokedexState.Loading -> LoadingContent()
            is PokedexState.Result ->
                PokedexBody(
                    state = result.state,
                    strings = strings,
                    image = { id -> screenModel.getImageURL(id) },
                    onValueChange = { inputText ->
                        screenModel.updateInputText(result.state, inputText)
                    },
                    onClickAction = { inputText ->
                        screenModel.getPokemonByNameOrId(inputText, result.state)
                    },
                    onClickBadge = { preSelected, id ->
                        screenModel.updateSelectedType(
                            id = id,
                            preSelectedType = preSelected,
                            stateData = result.state
                        )
                    },
                    onClickToDetail = { id ->
                        navigator.replaceAll(DetailScreen(result.state, id))
                    },
                    loadMore = { screenModel.loadMoreList(result.state) },
                    onClick = { screenModel.reloadAction(result.state) }
                )

            null -> {}
        }

        LifecycleEffectOnce {
            screenModel.getList(preSelectedId)
        }
    }

    @Composable
    private fun PokedexBody(
        state: PokedexStateData,
        image: (String) -> String,
        strings: PokedexStrings,
        onClickAction: (String) -> Unit,
        onClick: () -> Unit,
        onValueChange: (String) -> Unit,
        onClickBadge: (String?, String) -> Unit,
        onClickToDetail: (String) -> Unit,
        loadMore: () -> Unit,
    ) {
        val msg = state.errorMsg

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Header(strings.list)
            SearchInputAndTypesBadges(
                inputText = state.inputText,
                types = state.typeList,
                strings = strings.list,
                selectedType = state.selectedType,
                onValueChange = onValueChange,
                onClickAction = onClickAction,
                onClickBadge = onClickBadge,
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = MaterialTheme.colorScheme.secondary,
            )
            if (msg != null)
                EmptyScreen(
                    msg = msg,
                    strings = strings,
                    image = image,
                    onClickAction = onClick,
                )
            else
                EndlessLazyColumn(
                    items = state.list,
                    itemKey = { pokemon -> pokemon.id },
                    itemContent = { pokemon ->
                        PokemonCard(
                            pokemon = pokemon,
                            image = image,
                            onClickToDetail = onClickToDetail,
                        )
                    },
                    loadMore = loadMore,
                )
        }
    }
}