package com.example.pokedex.ui.step.components.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.ui.PokedexListStrings

@Composable
fun Header(
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
            color = theme.secondary
        )

        Text(
            text = strings.description,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif,
            fontSize = TextUnit(value = 16.sp.value, type = TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = theme.surface
        )
    }
}