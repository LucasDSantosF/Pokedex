package com.example.pokedex.ui.step.components.detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
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

@Composable
fun PokemonInfo(
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
            fontSize = TextUnit(value = 26f, type = TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = theme.secondary.takeIf { isSystemInDarkTheme() } ?: theme.surface,
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