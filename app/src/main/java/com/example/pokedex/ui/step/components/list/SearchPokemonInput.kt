package com.example.pokedex.ui.step.components.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
@OptIn(ExperimentalMaterial3Api::class)
fun SearchPokemonInput(
    inputText: String,
    strings: PokedexListStrings,
    onClickAction: (String) -> Unit,
    onValueChange: (String) -> Unit,
    theme: ColorScheme = MaterialTheme.colorScheme,
) {
    TextField(
        modifier = Modifier
            .border(
                color = theme.primary,
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        value = inputText,
        shape = RoundedCornerShape(8.dp),
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = theme.primary,
            )
        },
        keyboardActions = KeyboardActions(
            onDone = { onClickAction(inputText) },
        ),
        placeholder = {
            Text(
                text = strings.placeholder,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 16.sp.value, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = theme.primary
            )
        },
        textStyle =
            LocalTextStyle.current.copy(
                color = theme.primary,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
                fontSize = TextUnit(value = 20.sp.value, type = TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
            ),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = theme.secondary,
            unfocusedContainerColor = theme.secondary,
            cursorColor = theme.primary,
            focusedIndicatorColor = theme.primary,
            unfocusedIndicatorColor = theme.secondary
        ),
        singleLine = true,
    )
}