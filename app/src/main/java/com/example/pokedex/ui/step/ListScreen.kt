package com.example.pokedex.ui.step

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.ui.PokedexListStrings
import com.example.pokedex.ui.PokedexStrings
import com.example.pokedex.ui.step.components.EmptyScreen
import com.example.pokedex.ui.step.components.LoadingContent
import com.example.pokedex.ui.util.EndlessLazyColumn

class ListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<PokedexFlowModel>()
        val state by screenModel.state.observeAsState()
        val strings = remember { PokedexStrings() }
        val selectedType: MutableState<String?> = remember { mutableStateOf(null) }
        val stateData: MutableState<PokedexStateData> = remember { mutableStateOf(PokedexStateData()) }

        when (val result = state) {
            PokedexState.Loading -> LoadingContent()
            is PokedexState.Result -> {
                PokedexBody(
                    state = result.state,
                    strings = strings,
                    image = remember { { id -> screenModel.getImageURL(id) } },
                    onValueChange = remember {
                        { inputText -> screenModel.updateInputText(result.state, inputText) }
                    },
                    onClickAction = remember {
                        {
                            screenModel.getPokemonByNameOrId(result.state.inputText, result.state)
                        }
                    },
                    onClickBadge = remember {
                        { id ->
                            screenModel.updateSelectedType(id = id, stateData = result.state)
                            screenModel.getPokemonByType(result.state, id)
                        }
                    },
                    onClickToDetail = remember {
                        { id -> navigator.push(DetailScreen(result.state, id)) }
                    },
                    loadMore = remember { { screenModel.loadMoreList(result.state) } },
                    onClick = remember { { screenModel.reloadAction(result.state) } }
                )
                selectedType.value = result.state.selectedType
                stateData.value = result.state
            }

            null -> {}
        }

        LaunchedEffect(key1 = screenModel) {
            selectedType.value?.let { id ->
                screenModel.getPokemonByType(stateData.value, id)
            } ?: screenModel.getList()
        }
    }


    @Composable
    private fun PokedexBody(
        state: PokedexStateData,
        image: (String) -> String,
        strings: PokedexStrings,
        onClickAction: () -> Unit,
        onClick: () -> Unit,
        onValueChange: (String) -> Unit,
        onClickBadge: (String) -> Unit,
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


    @Composable
    private fun PokemonCard(
        pokemon: Pokemon,
        image: (String) -> String,
        onClickToDetail: (String) -> Unit,
        theme: ColorScheme = MaterialTheme.colorScheme,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal =40.dp)
                    .clickable { onClickToDetail(pokemon.id) }
            ) {
                Column(
                    Modifier.fillMaxWidth()
                        .background(color = theme.surface)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = image(pokemon.id),
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(250.dp)
                            .width(210.dp)
                    )
                    Text(
                        modifier = Modifier.offset(y = (-12).dp),
                        text = pokemon.name,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = TextUnit(value = 20.sp.value, type = TextUnitType.Sp),
                        fontWeight = FontWeight.ExtraBold,
                        color = theme.tertiary
                    )
                }
            }
        }
    }

    @Composable
    private fun SearchInputAndTypesBadges(
        inputText: String,
        strings: PokedexListStrings,
        types: List<PokemonType>,
        selectedType: String?,
        onClickAction: () -> Unit,
        onValueChange: (String) -> Unit,
        onClickBadge: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchPokemonInput(
                inputText = inputText,
                strings = strings,
                onClickAction = onClickAction,
                onValueChange = onValueChange,
            )
            LazyRow(modifier = Modifier.padding(horizontal = 4.dp)) {
                items(items = types) { type ->
                    TypeBadge(
                        type = type,
                        onClickBadge = onClickBadge,
                        selectedType = selectedType,
                    )
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun SearchPokemonInput(
        inputText: String,
        strings: PokedexListStrings,
        onClickAction: () -> Unit,
        onValueChange: (String) -> Unit,
        theme: ColorScheme = MaterialTheme.colorScheme,
    ) {
        TextField(
            modifier = Modifier
                .border(
                    color = theme.primary,
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            value = inputText,
            shape = RoundedCornerShape(8.dp),
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = theme.tertiary,
                )
            },
            keyboardActions = KeyboardActions(
                onDone = { onClickAction() },
            ),
            placeholder = {
                Text(
                    text = strings.placeholder,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = TextUnit(value = 16.sp.value, type = TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = theme.tertiary
                )
            },
            textStyle =
                LocalTextStyle.current.copy(
                    color = theme.tertiary,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = TextUnit(value = 20.sp.value, type = TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                ),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = theme.surface,
                unfocusedContainerColor = theme.surface,
                cursorColor = theme.primary,
                focusedIndicatorColor = theme.surface,
                unfocusedIndicatorColor = theme.surface,
            ),
            singleLine = true,
        )
    }

    @Composable
    private fun TypeBadge(
        type: PokemonType,
        selectedType: String?,
        onClickBadge: (String) -> Unit
    ) {
        val color = MaterialTheme.colorScheme.secondary

        Badge(
            Modifier
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clip(RoundedCornerShape(124.dp))
                .clickable { onClickBadge(type.id) }
                .conditional(selectedType == type.id) {
                    border(
                        border = BorderStroke(
                            width = 2.dp,
                            color = color,
                        ),
                        shape = RoundedCornerShape(124.dp)
                    )
                },
            containerColor = type.color,
            contentColor = type.color,
            content = {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    text = type.name,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = TextUnit(value = 16.sp.value, type = TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        )
    }

    @Composable
    private fun Header(
        strings: PokedexListStrings,
        theme: ColorScheme = MaterialTheme.colorScheme
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = strings.title,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 40.sp.value, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.surface
            )

            Text(
                text = strings.description,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 16.sp.value, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.secondary
            )
        }
    }

    private fun Modifier.conditional(
        condition: Boolean,
        modifier: Modifier.() -> Modifier
    ): Modifier {
        return if (condition) {
            then(modifier(Modifier))
        } else {
            this
        }
    }
}