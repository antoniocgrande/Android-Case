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
    private val BASE_URL = "https://geo.api.truckpad.io"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

}