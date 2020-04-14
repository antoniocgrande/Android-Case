package br.com.antoniocgrande.truckpad_case.data.network

import br.com.antoniocgrande.truckpad_case.data.request.AnttPriceRequest
import br.com.antoniocgrande.truckpad_case.data.request.RouteRequest
import br.com.antoniocgrande.truckpad_case.data.response.AnttPriceResponse
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data.network
 * Created by  : antoniocgrande
 * Date        : 07/04/2020 11:20
 ************************************************************/
interface GetDataService {

    @POST("/v1/route")
    fun calcCost(@Body routeRequest: RouteRequest): Call<RouteResponse?>?

    @POST("/v1/antt_price/all")
    fun getAnttPrice(@Body anttPriceRequest: AnttPriceRequest): Call<AnttPriceResponse?>?

}