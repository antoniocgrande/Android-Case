package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import br.com.antoniocgrande.truckpad_case.R
import br.com.antoniocgrande.truckpad_case.data.request.Place
import br.com.antoniocgrande.truckpad_case.data.request.RouteRequest
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import br.com.antoniocgrande.truckpad_case.ui.activities.PlacesGeoActivity
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.INITIAL_AXIS
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.PERMISSION_REQUEST
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.PLACE_ADD_DESTINO
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.PLACE_ADD_ORIGEM
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.REQUEST_ADDRESS_DESTINO
import br.com.antoniocgrande.truckpad_case.utils.Constants.Companion.REQUEST_ADDRESS_ORIGEN
import com.google.android.gms.common.api.Status
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment() {

    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false


    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val viewModel: HomeViewModel by lazy { HomeViewModel() }
    private val routeRequest by lazy { RouteRequest() }
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
        routeRequest.axis = INITIAL_AXIS
        routeRequest.places = places

        disableView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                enableView()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {
            enableView()
        }
    }

    private fun disableView() {
        imageViewCurrentOrigem.isEnabled = false
        imageViewCurrentOrigem.alpha = 0.5F
    }

    private fun enableView() {
        imageViewCurrentOrigem.isEnabled = true
        imageViewCurrentOrigem.alpha = 1F
        imageViewCurrentOrigem.setOnClickListener { getLocation() }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val lat = location.latitude
            val lon = location.longitude
            val address = geocoder.getFromLocation(lat, lon, 1)
            places[PLACE_ADD_ORIGEM] = Place(
                arrayListOf(
                    location.longitude.toFloat(),
                    location.latitude.toFloat()
                )
            )
            routeRequest.places = places
            routeRequest.literalOriginAddress = address[0].getAddressLine(0)
            textInputEditTextOrigem.setText(routeRequest.literalOriginAddress)
            buttonCalcularCustosEnability()
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (requireContext().checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Go to settings and enable the permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            if (allSuccess)
                enableView()
        }
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
                                    buttonCalcularCustosEnability()
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
                                    buttonCalcularCustosEnability()
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
        imageViewClearOrigem.setOnClickListener {
            textInputEditTextOrigem.setText("")
            buttonCalcularCustosEnability()
        }
        imageViewClearDestino.setOnClickListener {
            textInputEditTextDestino.setText("")
            buttonCalcularCustosEnability()
        }
        imageViewClearConsumo.setOnClickListener {
            textInputEditTextConsumo.setText(
                resources.getString(
                    R.string.consumo_medio_valor_padrao
                )
            )
        }
        imageViewClearPreco.setOnClickListener {
            textInputEditTextPreco.setText(
                resources.getString(
                    R.string.preco_litro_do_diesel_valor_padrao
                )
            )
        }
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
        numberPickerEixos.setValueChangedListener { value, action ->
            routeRequest.axis = value
        }
        textInputEditTextConsumo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                buttonCalcularCustosEnability()
                routeRequest.fuelConsumption = when {
                    !p0.isNullOrEmpty() -> p0.toString().toFloat()
                    else -> return
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        textInputEditTextPreco.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                buttonCalcularCustosEnability()
                routeRequest.fuelPrice = when {
                    !p0.isNullOrEmpty() -> p0.toString().toFloat()
                    else -> return
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        buttonCalcularCustos.setOnClickListener {
            viewModel.calcCost(routeRequest)
        }
    }

    private fun buttonCalcularCustosEnability() {
        when {
            textInputEditTextOrigem.text.isNullOrEmpty() or
                    textInputEditTextDestino.text.isNullOrEmpty() or
                    textInputEditTextConsumo.text.isNullOrEmpty() or
                    textInputEditTextPreco.text.isNullOrEmpty() -> {
                buttonCalcularCustos.isEnabled = false
            }
            else -> buttonCalcularCustos.isEnabled = true
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
