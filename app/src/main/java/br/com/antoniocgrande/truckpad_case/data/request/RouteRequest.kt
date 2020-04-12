package br.com.antoniocgrande.truckpad_case.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data
 * Created by  : antoniocgrande
 * Date        : 07/04/2020 07:48
 ************************************************************/
data class RouteRequest(
    @SerializedName("places") var places: MutableList<Place>? = null,
    @SerializedName("fuel_consumption") var fuelConsumption: Float? = null,
    @SerializedName("fuel_price") var fuelPrice: Float? = null,

    var literalOriginAddress: String? = null,
    var literalDetinationAddress: String? = null
) : Serializable