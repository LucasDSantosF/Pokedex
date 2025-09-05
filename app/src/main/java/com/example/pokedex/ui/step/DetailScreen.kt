package com.example.pokedex.ui.step

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonStats
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.model.models.TypeColors
import com.example.pokedex.ui.PokedexDetailStrings
import com.example.pokedex.ui.PokedexStrings
import com.example.pokedex.ui.step.components.EmptyScreen
import com.example.pokedex.ui.step.components.LoadingContent

data class DetailScreen(val stateData: PokedexStateData, val id: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<PokedexFlowModel>()
        val state by screenModel.state.observeAsState()
        val strings = remember { PokedexStrings() }

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
                        backAction = remember { { navigator.pop() } },
                        onClickBadge = remember {
                            { id ->
                                screenModel.updateSelectedType(id, true, result.state)
                                screenModel.getPokemonByType(result.state, id)
                                navigator.pop()
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
        Toolbar(
            name = pokemon.name,
            backAction = backAction,
            backGroundColor = pokemon.color,
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.tertiary, thickness = 2.dp)
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
                    .background(color = MaterialTheme.colorScheme.primary)
                    .weight(1f)
            ) {
                LazyColumn {
                    item {
                        PokemonImage(
                            pokemon = pokemon,
                            image = image,
                            imageHome = imageHome,
                        )
                        PokemonInfo(
                            pokemon = pokemon,
                            strings = strings.details,
                            onClickBadge = onClickBadge,
                        )
                    }
                }
            }
    }

    @Composable
    private fun PokemonInfo(
        pokemon: PokemonDetail,
        strings: PokedexDetailStrings,
        onClickBadge: (String) -> Unit,
        theme: ColorScheme = MaterialTheme.colorScheme,
    ) {
        Column {
            Spacer(modifier = Modifier.padding(10.dp))
            PokemonTypeRow(
                pokemon = pokemon,
                strings = strings,
                onClickBadge = onClickBadge,
            )

            Text(
                text = strings.statLabel,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 28f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.surface,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
            )
            Column(modifier = Modifier.padding(20.dp)) {
                pokemon.stats.forEachIndexed { index, stat ->
                    if (index != 0)
                        HorizontalDivider(
                            color = theme.surface,
                            thickness = 2.dp
                        )
                    PokemonStatsRow(stat = stat)
                }
            }
        }
    }

    @Composable
    private fun PokemonStatsRow(
        stat: PokemonStats,
        theme: ColorScheme = MaterialTheme.colorScheme,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stat.stat.name,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 24f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.secondary
            )

            Text(
                text = stat.baseStat,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 24f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.End,
                color = theme.surface
            )
        }
    }

    @Composable
    private fun PokemonTypeRow(
        pokemon: PokemonDetail,
        strings: PokedexDetailStrings,
        onClickBadge: (String) -> Unit,
        theme: ColorScheme = MaterialTheme.colorScheme,
    ) {
        Column {
            Text(
                text = strings.typeLabel,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 28f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.surface,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
            )
            LazyRow(
                modifier = Modifier.padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(items = pokemon.types) { type ->
                    Column(modifier = Modifier.padding(20.dp)) {
                        TypeBadge(
                            type = type,
                            onClickBadge = onClickBadge,
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun PokemonImage(
        pokemon: PokemonDetail,
        image: (String) -> String,
        imageHome: (String) -> String,
        theme: ColorScheme = MaterialTheme.colorScheme,
    ) {
        Column {
            LazyRow(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = pokemon.color)
            ) {
                item {
                    AsyncImage(
                        model = image(pokemon.id),
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .background(color = pokemon.color)
                            .height(350.dp)
                            .width(300.dp)
                    )
                    AsyncImage(
                        model = imageHome(pokemon.id),
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .background(color = pokemon.color)
                            .height(250.dp)
                            .width(300.dp)
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = pokemon.color)
                    .offset(y = (-45).dp),
                text = pokemon.number,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 28f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = theme.tertiary
            )
            HorizontalDivider(
                color = theme.surface,
                thickness = 2.dp
            )
        }
    }

    @Composable
    private fun TypeBadge(
        type: PokemonType,
        onClickBadge: (String) -> Unit
    ) {
        Badge(
            modifier = Modifier
                .clip(RoundedCornerShape(124.dp))
                .clickable { onClickBadge(type.id) },
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
            })
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Toolbar(
        name: String,
        backAction: () -> Unit,
        backGroundColor: Color,
        theme: ColorScheme = MaterialTheme.colorScheme,
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = backGroundColor,
            ),
            title = {
                Text(
                    text = name,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = TextUnit(value = 28f, type = TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = theme.tertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            navigationIcon = {
                IconButton(onClick = { backAction() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = theme.tertiary,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}
