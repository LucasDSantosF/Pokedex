package com.example.pokedex.di

import org.kodein.di.DI

object AppInjection {
    private const val MODULE_NAME = "app"

    val injection = DI.Module(MODULE_NAME) {}
}