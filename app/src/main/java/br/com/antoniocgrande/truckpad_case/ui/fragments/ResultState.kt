package br.com.antoniocgrande.truckpad_case.ui.fragments

import br.com.antoniocgrande.truckpad_case.data.response.AnttPriceResponse
import retrofit2.Response

/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.ui.fragments
 * Created by  : antoniocgrande
 * Date        : 03/04/2020 23:24
 ************************************************************/
internal sealed class ResultState {

    object ShowLoading : ResultState()

    object HideLoading : ResultState()

    data class AnttPriceSuccess(val response: Response<AnttPriceResponse?>) : ResultState()

    data class AnttPriceError(val message: String?) : ResultState()

}