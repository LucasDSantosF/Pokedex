package com.example.pokedex.ui.step.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.models.PokemonDetail
import com.example.pokedex.ui.PokedexDetailStrings
import com.example.pokedex.ui.step.components.list.TypeBadge

@Composable
fun PokemonTypeRow(
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
            fontSize = TextUnit(value = 26f, type = TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = theme.secondary,
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