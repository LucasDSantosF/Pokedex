package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.example.pokedex.ui.step.ListScreen
import com.example.pokedex.ui.theme.PokedexTheme
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class MainActivity : ComponentActivity(), DIAware {

    override val di by closestDI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Navigator(screen = ListScreen())
            }
        }
    }
}