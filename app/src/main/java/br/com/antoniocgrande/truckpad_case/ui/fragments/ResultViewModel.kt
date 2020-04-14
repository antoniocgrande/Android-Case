package br.com.antoniocgrande.truckpad_case.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.antoniocgrande.truckpad_case.data.network.GetDataService
import br.com.antoniocgrande.truckpad_case.data.network.RetrofitClientInstance
import br.com.antoniocgrande.truckpad_case.data.request.AnttPriceRequest
import br.com.antoniocgrande.truckpad_case.data.response.AnttPriceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel : ViewModel() {

    private val _state = MutableLiveData<ResultState>()
    private val dataService =
        RetrofitClientInstance().getAnttPriceInstance()?.create(GetDataService::class.java)
    internal val getState: LiveData<ResultState> = _state

    fun getAnttPrice(anttPriceRequest: AnttPriceRequest) {
        _state.value = ResultState.ShowLoading
        dataService?.getAnttPrice(anttPriceRequest).apply {
            this?.enqueue(object : Callback<AnttPriceResponse?> {
                override fun onFailure(call: Call<AnttPriceResponse?>, t: Throwable) {
                    _state.value = ResultState.AnttPriceError(t.message)
                    _state.value = ResultState.HideLoading
                }

                override fun onResponse(
                    call: Call<AnttPriceResponse?>,
                    response: Response<AnttPriceResponse?>
                ) {
                    _state.value = ResultState.AnttPriceSuccess(response)
                    _state.value = ResultState.HideLoading
                }
            })
        }
    }

}
