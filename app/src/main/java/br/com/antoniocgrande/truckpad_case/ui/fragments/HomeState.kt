package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.location.Location

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

    object GotoResult : HomeState()

    data class GpsCurrentLocation(val currentLocation: Location) : HomeState()

}