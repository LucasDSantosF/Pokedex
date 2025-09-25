package com.example.pokedex.ui.step.components.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.models.PokemonType

@Composable
fun TypeBadge(
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