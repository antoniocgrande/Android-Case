package br.com.antoniocgrande.truckpad_case.data

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data
 * Created by  : antoniocgrande
 * Date        : 07/04/2020 07:48
 ************************************************************/
data class Route(
    var places: MutableList<Place>? = null,
    var fuelConsumption: Int,
    var fuelPrice: Float
)