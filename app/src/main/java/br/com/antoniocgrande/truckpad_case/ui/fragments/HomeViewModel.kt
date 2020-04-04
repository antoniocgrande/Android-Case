package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val state = MutableLiveData<HomeState>()

    internal val getState: LiveData<HomeState> = state

    fun gotoResult() = state.postValue(HomeState.GotoResult)

    fun getGpsCurrentLocation() {

    }

    fun addressList() {

    }

    fun calcCost() {

    }

}
