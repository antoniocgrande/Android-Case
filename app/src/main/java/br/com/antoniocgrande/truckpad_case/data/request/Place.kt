package br.com.antoniocgrande.truckpad_case.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data
 * Created by  : antoniocgrande
 * Date        : 07/04/2020 07:50
 ************************************************************/
data class Place(
    @SerializedName("point") var point: List<Float?>? = null
) : Serializable