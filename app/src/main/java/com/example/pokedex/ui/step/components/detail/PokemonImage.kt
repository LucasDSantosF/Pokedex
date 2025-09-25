package com.example.pokedex.ui.step.components.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.model.models.PokemonDetail

@Composable
fun PokemonImage(
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
                        .height(270.dp)
                        .width(300.dp)
                )
                AsyncImage(
                    model = imageHome(pokemon.id),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .background(color = pokemon.color)
                        .height(350.dp)
                        .width(300.dp)
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = pokemon.color)
                .offset(y = (-35).dp),
            text = "#${pokemon.number}",
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif,
            fontSize = TextUnit(value = 28f, type = TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = theme.tertiary
        )
    }
}