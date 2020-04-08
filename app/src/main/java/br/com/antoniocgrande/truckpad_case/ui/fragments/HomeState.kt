package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.location.Location
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import retrofit2.Response

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.ui.fragments
 * Created by  : antoniocgrande
 * Date        : 03/04/2020 23:24
 ************************************************************/
internal sealed class HomeState {

    object ShowLoading : HomeState()

    object HideLoading : HomeState()

    data class CalcCostSuccess(val response: Response<RouteResponse?>) : HomeState()

    data class CalcCostError(val message: String?) : HomeState()

    data class GpsCurrentLocation(val currentLocation: Location) : HomeState()

}