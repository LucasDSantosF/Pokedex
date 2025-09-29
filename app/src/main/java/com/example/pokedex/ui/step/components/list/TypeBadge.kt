package com.example.pokedex.ui.step.components.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.ui.util.conditional

@Composable
fun TypeBadge(
    type: PokemonType,
    selectedType: String?,
    onClickBadge: ( String?, String) -> Unit
) {
    val color = MaterialTheme.colorScheme.surface

    Badge(
        Modifier
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(124.dp))
            .clickable { onClickBadge(selectedType, type.id) }
            .conditional(selectedType == type.id) {
                border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = color,
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
                fontSize = TextUnit(value = 16.sp.value, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}