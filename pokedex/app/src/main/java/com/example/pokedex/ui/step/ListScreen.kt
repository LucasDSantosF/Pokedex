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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.ui.PokedexListStrings
import com.example.pokedex.ui.PokedexStrings
import com.example.pokedex.ui.step.components.EmptyScreen
import com.example.pokedex.ui.util.EndlessLazyColumn

class ListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<PokedexFlowModel>()
        val state by screenModel.state.collectAsState()
        val strings = remember { PokedexStrings() }

        LaunchedEffect(key1 = screenModel) {
            state.selectedType?.let { id ->
                screenModel.getPokemonByType(id)
            } ?: screenModel.getList()
        }

        PokedexBody(
            state = state,
            strings = strings,
            image = remember { { id -> screenModel.getImageURL(id) } },
            onValueChange = remember { { inputText -> screenModel.updateInputText(inputText) } },
            onClickAction = remember { { screenModel.getPokemonByNameOrId() } },
            onClickBadge = remember {
                { id ->
                    screenModel.updateSelectedType(id)
                    screenModel.getPokemonByType(id)
                }
            },
            onClickToDetail = remember { { id -> navigator.push(DetailScreen(id)) } },
            loadMore = remember { { screenModel.loadMoreList() } },
            onClick = remember { { screenModel.reloadAction() } }
        )
    }


    @Composable
    private fun PokedexBody(
        state: PokedexState,
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
                .background(color = Color(red = 255, green = 250, blue = 250))
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
        onClickToDetail: (String) -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable { onClickToDetail(pokemon.id) }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        fontSize = TextUnit(value = 20f, type = TextUnitType.Sp),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(red = 38, green = 0, blue = 65)
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
    ) {
        TextField(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            value = inputText,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = Color(red = 38, green = 0, blue = 65),
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
                    fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(red = 160, green = 160, blue = 160)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(red = 240, green = 240, blue = 240),
                focusedIndicatorColor = Color(red = 240, green = 240, blue = 240),
                unfocusedIndicatorColor = Color(red = 240, green = 240, blue = 240),
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
        Badge(
            Modifier
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clip(RoundedCornerShape(124.dp))
                .clickable { onClickBadge(type.id) }
                .conditional(selectedType == type.id) {
                    border(
                        border = BorderStroke(
                            width = 2.dp,
                            color = Color(red = 38, green = 0, blue = 65)
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
                    fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        )
    }

    @Composable
    private fun Header(strings: PokedexListStrings) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = strings.title,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 40f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color(red = 38, green = 0, blue = 65)
            )

            Text(
                text = strings.description,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color(red = 160, green = 160, blue = 160)
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