package br.com.antoniocgrande.truckpad_case.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.antoniocgrande.truckpad_case.data.network.GetDataService
import br.com.antoniocgrande.truckpad_case.data.network.RetrofitClientInstance
import br.com.antoniocgrande.truckpad_case.data.request.RouteRequest
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    private val dataService =
        RetrofitClientInstance().getRetrofitInstance()?.create(GetDataService::class.java)
    internal val getState: LiveData<HomeState> = _state

    fun getGpsCurrentLocation() {

    }

    fun addressList() {

    }

    fun calcCost(routeRequest: RouteRequest) {
        dataService?.calcCost(routeRequest).apply {
            this?.enqueue(object : Callback<RouteResponse?> {
                override fun onFailure(call: Call<RouteResponse?>, t: Throwable) {
                    _state.value = HomeState.CalcCostError(t.message)
                }

                override fun onResponse(
                    call: Call<RouteResponse?>,
                    response: Response<RouteResponse?>
                ) {
                    _state.value = HomeState.CalcCostSuccess(response)
                }

            })
        }
    }

}
