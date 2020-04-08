package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import br.com.antoniocgrande.truckpad_case.R
import br.com.antoniocgrande.truckpad_case.data.request.Place
import br.com.antoniocgrande.truckpad_case.data.request.Route
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Response

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy { HomeViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupState()
        setupListeners()
    }

    /* SETUP FUNCTIONS SECTOR */
    private fun setupState() {
        viewModel.getState.observe(this, Observer { state ->
            when (state) {
                is HomeState.ShowLoading -> showLoading()
                is HomeState.HideLoading -> hideLoading()
                is HomeState.CalcCostSuccess -> gotoResult(state.response)
                is HomeState.CalcCostError -> showError(state.message)
            }
        })
    }

    private fun setupListeners() {
        clearText(imageViewClearOrigem, textInputEditTextOrigem)
        clearText(imageViewClearDestino, textInputEditTextDestino)
        clearText(
            imageViewClearConsumo,
            textInputEditTextConsumo,
            resources.getString(R.string._7_5)
        )
        clearText(imageViewClearPreco, textInputEditTextPreco, resources.getString(R.string._3_73))


        /* mock payload */
        val places = mutableListOf<Place>()
        places.add(
            Place(
                arrayListOf(-46.68664F, -23.59496F)
            )
        )
        places.add(
            Place(
                arrayListOf(-46.67678F, -23.59867F)
            )
        )
        val route = Route(
            places,
            7F,
            3.4F
        )

//        {
//            "fuelConsumption":7,
//            "fuelPrice":3.4,
//            "places":[
//            {
//                "point":[
//                -46.68664,
//                -23.59496
//                ]
//            }]
//        }

        buttonCalcularCustos.setOnClickListener { viewModel.calcCost(route) }
    }

    private fun clearText(
        imageViewClear: ImageView,
        textInputEditText: TextInputEditText,
        defaultValue: String = ""
    ) {
        imageViewClear.setOnClickListener {
            textInputEditText.setText(defaultValue)
        }
    }

    /* STATE FUNCTIONS SECTOR */
    private fun showLoading() {
        linearLayoutProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        linearLayoutProgressBar.visibility = View.GONE
    }

    private fun gotoResult(response: Response<RouteResponse?>) = Navigation.findNavController(
        requireActivity(),
        R.id.navHostFragment
    ).navigate(R.id.action_homeFragment_to_resultFragment)

    private fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
