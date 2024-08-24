package com.example.pokedex.di

import com.example.pokedex.data.PokedexInfrastructure
import com.example.pokedex.data.gateway.Gateway
import com.example.pokedex.data.gatewayBuilder.RetrofitBuilder
import com.example.pokedex.data.gatewayBuilder.RetrofitBuilder.buildGateway
import com.example.pokedex.model.service.PokedexService
import okhttp3.OkHttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit

object AppInjection {
    private const val MODULE_NAME = "app"

    val injection = DI.Module(MODULE_NAME) {
        bind<OkHttpClient>() with singleton {
            OkHttpClient
                .Builder()
                .build()
        }

        bind<Retrofit>() with singleton {
            RetrofitBuilder.invoke(instance())
        }

        bind<Gateway>() with singleton {
            val retrofit = instance<Retrofit>()
            retrofit.buildGateway()
        }

        bind<PokedexService>() with singleton {
            PokedexInfrastructure(
                gateway = instance()
            )
        }
    }
}