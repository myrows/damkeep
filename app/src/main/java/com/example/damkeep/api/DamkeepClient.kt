package com.example.damkeep.api

import com.example.damkeep.common.Constantes
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DamkeepClient {

    private val damkeepService: DamkeepService
    private val retrofit: Retrofit

    init {
        // Incluir el interceptor que hemos definido

        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(DamkeepInterceptor())

        val cliente = okHttpClientBuilder.build()

        // Construir el cliente de Retrofit
        retrofit = Retrofit.Builder()
            .baseUrl(Constantes.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(cliente)
            .build()

        // Instanciamos el servicio de Retrofit a partir del objeto retrofit
        damkeepService = retrofit.create(DamkeepService::class.java)
    }

    fun getDamkeepService() = damkeepService
}