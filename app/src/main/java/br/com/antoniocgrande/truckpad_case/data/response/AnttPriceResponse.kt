package br.com.antoniocgrande.truckpad_case.data.response

import com.google.gson.annotations.SerializedName

/* Copyright 2020.
************************************************************
* Project     : TruckPad-Case
* Description : br.com.antoniocgrande.truckpad_case.data.response
* Created by  : antoniocgrande
* Date        : 13/04/2020 19:38
************************************************************/
data class AnttPriceResponse(
    @SerializedName("frigorificada") var frigorificada: Float? = null,
    @SerializedName("geral") var geral: Float? = null,
    @SerializedName("granel") var granel: Float? = null,
    @SerializedName("neogranel") var neogranel: Float? = null,
    @SerializedName("perigosa") var perigosa: Float? = null
)