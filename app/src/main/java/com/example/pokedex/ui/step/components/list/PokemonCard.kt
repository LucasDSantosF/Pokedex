package com.example.pokedex.ui.step.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedex.model.models.Pokemon

@Composable
fun PokemonCard(
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
                .padding(vertical = 20.dp, horizontal = 60.dp)
                .clickable { onClickToDetail(pokemon.id) }
        ) {
            Column(
                Modifier.fillMaxWidth()
                    .background(color = theme.secondary)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.offset(y = 12.dp),
                    text = "${pokemon.name} / #${pokemon.id}",
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = TextUnit(value = 20.sp.value, type = TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = theme.primary
                )
                AsyncImage(
                    model = image(pokemon.id),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(220.dp)
                        .width(210.dp)
                )
            }
        }
    }
}