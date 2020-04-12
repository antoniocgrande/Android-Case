package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import br.com.antoniocgrande.truckpad_case.R
import br.com.antoniocgrande.truckpad_case.data.request.Place
import br.com.antoniocgrande.truckpad_case.data.request.RouteRequest
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import br.com.antoniocgrande.truckpad_case.ui.activities.PlacesGeoActivity
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.PLACE_ADD_DESTINO
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.PLACE_ADD_ORIGEM
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.REQUEST_ADDRESS_DESTINO
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.REQUEST_ADDRESS_ORIGEN
import com.google.android.gms.common.api.Status
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Response


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy { HomeViewModel() }
    private val routeRequest by lazy { RouteRequest(null, 7.5F, 3.6F) }
    private val places = mutableListOf(Place(arrayListOf(0F, 0F)), Place(arrayListOf(0F, 0F)))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupState()
        setupListeners()
        routeRequest.places = places
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    when {
                        it.hasExtra("PLACE_SUCCESS") -> {
                            when (requestCode) {
                                REQUEST_ADDRESS_ORIGEN -> {
                                    val placeSuccess =
                                        it.getParcelableExtra<Parcelable>("PLACE_SUCCESS") as com.google.android.libraries.places.api.model.Place
                                    places[PLACE_ADD_ORIGEM] = Place(
                                        arrayListOf(
                                            placeSuccess.latLng?.longitude?.toFloat(),
                                            placeSuccess.latLng?.latitude?.toFloat()
                                        )
                                    )
                                    routeRequest.places = places
                                    routeRequest.literalOriginAddress = placeSuccess.name
                                    textInputEditTextOrigem.setText(routeRequest.literalOriginAddress)
                                }
                                REQUEST_ADDRESS_DESTINO -> {
                                    val placeSuccess =
                                        it.getParcelableExtra<Parcelable>("PLACE_SUCCESS") as com.google.android.libraries.places.api.model.Place
                                    places[PLACE_ADD_DESTINO] = Place(
                                        arrayListOf(
                                            placeSuccess.latLng?.longitude?.toFloat(),
                                            placeSuccess.latLng?.latitude?.toFloat()
                                        )
                                    )
                                    routeRequest.places = places
                                    routeRequest.literalDetinationAddress = placeSuccess.name
                                    textInputEditTextDestino.setText(routeRequest.literalDetinationAddress)
                                }
                            }
                        }
                        it.hasExtra("PLACE_ERROR") -> {
                            val placeError =
                                it.getParcelableExtra<Parcelable>("PLACE_ERROR") as Status
                            Toast.makeText(
                                context,
                                "Algo deu errado. Porfavor tente novamente",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
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
            resources.getString(R.string.consumo_medio_valor_padrao)
        )
        clearText(
            imageViewClearPreco,
            textInputEditTextPreco,
            resources.getString(R.string.preco_litro_do_diesel_valor_padrao)
        )

        textInputEditTextOrigem.setOnClickListener {
            startActivityForResult(
                Intent(context, PlacesGeoActivity::class.java),
                REQUEST_ADDRESS_ORIGEN
            )
        }

        textInputEditTextDestino.setOnClickListener {
            startActivityForResult(
                Intent(context, PlacesGeoActivity::class.java),
                REQUEST_ADDRESS_DESTINO
            )
        }

        buttonCalcularCustos.setOnClickListener { viewModel.calcCost(routeRequest) }
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

    private fun gotoResult(response: Response<RouteResponse?>) {
        val navigation = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
        when (navigation.currentDestination?.id) {
            R.id.homeFragment -> navigation.navigate(
                R.id.action_homeFragment_to_resultFragment,
                Bundle().apply {
                    putSerializable("response", response.body())
                    putSerializable("request", routeRequest)
                    putSerializable("axis", numberPickerEixos.value.toString())
                }
            )
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
