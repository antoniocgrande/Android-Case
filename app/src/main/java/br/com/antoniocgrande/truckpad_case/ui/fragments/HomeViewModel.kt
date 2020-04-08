package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.antoniocgrande.truckpad_case.data.network.GetDataService
import br.com.antoniocgrande.truckpad_case.data.network.RetrofitClientInstance
import br.com.antoniocgrande.truckpad_case.data.network.RouteRepository
import br.com.antoniocgrande.truckpad_case.data.request.Route
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    internal val getState: LiveData<HomeState> = _state
    private val routeRepository by lazy { RouteRepository() }

    fun getGpsCurrentLocation() {

    }

    fun addressList() {

    }

    fun calcCost(route: Route) {
        val dataService =
            RetrofitClientInstance().getRetrofitInstance()?.create(GetDataService::class.java)
        val call: Call<RouteResponse?>? = dataService?.calcCost(route)
        call?.enqueue(object : Callback<RouteResponse?> {
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
