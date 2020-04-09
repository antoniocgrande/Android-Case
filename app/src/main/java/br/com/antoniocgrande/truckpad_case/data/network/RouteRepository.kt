package br.com.antoniocgrande.truckpad_case.data.network

import android.content.Context
import android.location.Address
import android.location.Geocoder
import br.com.antoniocgrande.truckpad_case.data.network.models.LatLng


/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data.network
 * Created by  : antoniocgrande
 * Date        : 09/04/2020 10:15
 ************************************************************/

class RouteRepository {

    fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.latitude
            location.longitude
            p1 = LatLng(location.latitude, location.longitude)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return p1
    }

}