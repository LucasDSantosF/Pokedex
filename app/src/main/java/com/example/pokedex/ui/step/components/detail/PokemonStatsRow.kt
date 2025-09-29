package com.example.pokedex.ui.step.components.detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.models.PokemonStats

@Composable
fun PokemonStatsRow(
    stat: PokemonStats,
    theme: ColorScheme = MaterialTheme.colorScheme,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedName(stat.stat.name).uppercase(),
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.secondary.takeIf { isSystemInDarkTheme() } ?: theme.surface
            )
            Text(
                text = stat.baseStat,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.End,
                color = theme.surface.takeIf { isSystemInDarkTheme() } ?: theme.tertiary
            )
        }
        LinearProgressIndicator(
            modifier = Modifier
                .width(200.dp)
                .height(8.dp),
            progress = { stat.baseStat.toFloat() /100 },
            color = getTrackColor(stat.baseStat.toInt()),
            trackColor = theme.surface,
            strokeCap = StrokeCap.Round
        )
    }
}

private fun formattedName(name: String) : String = when {
    name == "special-attack" -> "sp. atk"
    name == "special-defense" -> "sp. def"
    else -> name
}

private fun getTrackColor(value: Int) : Color =
    if (value >= 50) Color.Green else Color.Red