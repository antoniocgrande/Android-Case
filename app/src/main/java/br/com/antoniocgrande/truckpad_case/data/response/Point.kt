package br.com.antoniocgrande.truckpad_case.data.response

import com.google.gson.annotations.SerializedName


/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data.response
 * Created by  : antoniocgrande
 * Date        : 07/04/2020 22:27
 ************************************************************/
class Point {
    @SerializedName("point")
    var point: List<Float>? = null

    @SerializedName("provider")
    var provider: String? = null
}