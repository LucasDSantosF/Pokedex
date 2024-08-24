package com.example.pokedex.application

import android.app.Application
import com.example.pokedex.di.AppInjection
import org.kodein.di.DI
import org.kodein.di.DIAware

class App: Application(), DIAware {
    override val di = DI {
        import(AppInjection.injection)
    }
}