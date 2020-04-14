package br.com.antoniocgrande.truckpad_case.data.request

import com.google.gson.annotations.SerializedName

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data.request
 * Created by  : antoniocgrande
 * Date        : 13/04/2020 19:35
 ************************************************************/
data class AnttPriceRequest(
    @SerializedName("axis") var axis: Int? = null,
    @SerializedName("distance") var distance: Float? = null,
    @SerializedName("has_return_shipment") var has_return_shipment: Boolean? = null
)