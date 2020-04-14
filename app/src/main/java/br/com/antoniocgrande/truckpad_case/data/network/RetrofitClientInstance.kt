package br.com.antoniocgrande.truckpad_case.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data.network
 * Created by  : antoniocgrande
 * Date        : 07/04/2020 11:10
 ************************************************************/
class RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL_ROUTE = "https://geo.api.truckpad.io"
    private val BASE_URL_ANTT_PRICE = "https://tictac.api.truckpad.io"

    fun getRouteInstance(): Retrofit? {
        when (retrofit) {
            null -> retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_ROUTE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getAnttPriceInstance(): Retrofit? {
        when (retrofit) {
            null -> retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_ANTT_PRICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

}