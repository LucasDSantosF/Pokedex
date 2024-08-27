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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.model.models.PokemonStats
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.model.models.TypeColors
import com.example.pokedex.ui.PokedexDetailStrings
import com.example.pokedex.ui.PokedexStrings

data class DetailScreen(val id: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<PokedexFlowModel>()
        val state by screenModel.state.collectAsState()
        val strings = remember { PokedexStrings().details }

        LaunchedEffect(key1 = screenModel) {
            screenModel.getDetail(id)
        }

        Column {
            PokemonDetailBody(
                pokemon = state.details,
                strings = strings,
                image = remember { { id -> screenModel.getImageURL(id) } },
                backAction = remember { { navigator.pop() } },
                onClickBadge = remember { { id ->
                    screenModel.updateSelectedType(id, true)
                    screenModel.getPokemonByType(id)
                    navigator.pop()
                } }
            )
        }
    }

    @Composable
    private fun ColumnScope.PokemonDetailBody(
        pokemon: PokemonDetail,
        strings: PokedexDetailStrings,
        image: (String) -> String,
        backAction: () -> Unit,
        onClickBadge: (String) -> Unit
    ) {
        Toolbar(
            name = pokemon.name,
            backAction = backAction,
            backGroundColor = pokemon.color,
        )
        HorizontalDivider(color = Color(red = 38, green = 0, blue = 65), thickness = 2.dp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(red = 255, green = 250, blue = 250))
                .weight(1f)
        ) {
            LazyColumn {
                item {
                    PokemonImage(
                        pokemon = pokemon,
                        image = image,
                    )
                    PokemonInfo(
                        pokemon = pokemon,
                        strings = strings,
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
                color = Color(red = 38, green = 0, blue = 65),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
            )
            Column(modifier = Modifier.padding(20.dp)) {
                pokemon.stats.forEachIndexed { index, stat ->
                    if (index != 0)
                        HorizontalDivider(
                            color = Color(red = 38, green = 0, blue = 65),
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
                color = Color(red = 160, green = 160, blue = 160)
            )

            Text(
                text = stat.baseStat,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 24f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.End,
                color = Color(red = 100, green = 100, blue = 100)
            )
        }
    }

    @Composable
    private fun PokemonTypeRow(
        pokemon: PokemonDetail,
        strings: PokedexDetailStrings,
        onClickBadge: (String) -> Unit,
    ) {
        Column {
            Text(
                text = strings.typeLabel,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 28f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color(red = 38, green = 0, blue = 65),
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
    ) {
        Column {
            AsyncImage(
                model = image(pokemon.id.toString()),
                contentDescription = pokemon.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .background(color = pokemon.color)
                    .height(350.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider(
                color = Color(red = 38, green = 0, blue = 65),
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
                    color = Color(red = 38, green = 0, blue = 65),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            navigationIcon = {
                IconButton(onClick = { backAction() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color(red = 38, green = 0, blue = 65),
                        contentDescription = null,
                    )
                }
            },
        )
    }
}
