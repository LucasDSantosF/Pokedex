package com.example.pokedex.ui.step.components.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.models.PokemonType
import com.example.pokedex.ui.PokedexListStrings

@Composable
fun SearchInputAndTypesBadges(
    inputText: String,
    strings: PokedexListStrings,
    types: List<PokemonType>,
    selectedType: String?,
    onClickAction: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onClickBadge: (String?, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchPokemonInput(
            inputText = inputText,
            strings = strings,
            onClickAction = onClickAction,
            onValueChange = onValueChange,
        )
        LazyRow(modifier = Modifier.padding(horizontal = 4.dp)) {
            items(items = types) { type ->
                TypeBadge(
                    type = type,
                    onClickBadge = onClickBadge,
                    selectedType = selectedType,
                )
            }
        }
    }
}