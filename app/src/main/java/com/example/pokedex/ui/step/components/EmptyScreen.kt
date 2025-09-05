package com.example.pokedex.ui.step.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.pokedex.ui.PokedexStrings

@Composable
fun EmptyScreen(
    msg: String,
    strings: PokedexStrings,
    image: (String) -> String,
    onClickAction: () -> Unit,
    theme: ColorScheme = MaterialTheme.colorScheme,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = theme.primary)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
            modifier = Modifier.padding(20.dp),
            text = msg,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif,
            fontSize = TextUnit(value = 20f, type = TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = theme.tertiary.takeIf { isSystemInDarkTheme() } ?: theme.surface
        )
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = theme.secondary
            ),
            onClick = { onClickAction() }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = strings.tryAgain,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 20f, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.surface.takeIf { isSystemInDarkTheme() } ?: theme.tertiary
            )
        }
    }
}