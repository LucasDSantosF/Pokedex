package com.example.pokedex.ui.step.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.ui.PokedexStrings

@Composable
fun EmptyScreen(
    msg: String,
    strings: PokedexStrings,
    image: (String) -> String,
    onClickAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(red = 255, green = 250, blue = 250)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = image("17"),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(250.dp)
                .width(210.dp)
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = msg,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif,
            fontSize = TextUnit(value = 20f, type = TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = Color(red = 38, green = 0, blue = 65)
        )
        TextButton(
            modifier = Modifier.background(color = Color(red = 240, green = 240, blue = 240)),
            shape = RectangleShape,
            onClick = { onClickAction() }
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = strings.tryAgain,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 20f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color(red = 38, green = 0, blue = 65)
            )
        }
    }
}