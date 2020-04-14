package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import br.com.antoniocgrande.truckpad_case.R
import br.com.antoniocgrande.truckpad_case.data.request.AnttPriceRequest
import br.com.antoniocgrande.truckpad_case.data.request.RouteRequest
import br.com.antoniocgrande.truckpad_case.data.response.AnttPriceResponse
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_result.*
import retrofit2.Response

class ResultFragment : Fragment() {

    private val viewModel: ResultViewModel by lazy { ResultViewModel() }
    private val anttPriceRequest by lazy { AnttPriceRequest() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupState()
        setupListeners()

        arguments?.run {
            val response = getSerializable("response") as RouteResponse
            val request = getSerializable("request") as RouteRequest

            setResultValue(textViewOrigemValue, request.literalOriginAddress.toString())
            setResultValue(textViewDestinoValue, request.literalDetinationAddress.toString())
            setResultValue(textViewEixosValue, request.axis.toString())
            val seconds = response.duration ?: 0
            val min = seconds.div(60).rem(60)
            var minutos = ""
            minutos = when (min < 10) {
                true -> "0$min"
                false -> min.toString()
            }
            setResultValue(
                textViewDuracaoValue,
                "${seconds.div(60).div(60)}h${minutos}m"
            )
            setResultValue(textViewDistanciaValue, "${response.distance?.div(1000)} Km")
            setResultValue(textViewPedagioValue)
            setResultValue(textViewCombustivelNecessarioValue, "${response.fuelUsage} L")
            setResultValue(textViewTotalCombustivelValue, "R$ ${response.fuelCost}")
            setResultValue(textViewTotalValue, "R$ ${response.totalCost}")

            anttPriceRequest.axis = request.axis
            anttPriceRequest.distance = response.distance?.toFloat()
            anttPriceRequest.has_return_shipment = true

            viewModel.getAnttPrice(anttPriceRequest)
        }
    }

    /* SETUP FUNCTIONS SECTOR */
    private fun setupState() {
        viewModel.getState.observe(this, Observer { state ->
            when (state) {
                is ResultState.ShowLoading -> showLoading()
                is ResultState.HideLoading -> hideLoading()
                is ResultState.AnttPriceSuccess -> anttPriceSuccess(state.response)
                is ResultState.AnttPriceError -> anttPriceError(state.message)
            }
        })
    }

    private fun setupListeners() {
        buttonNovoCalculo.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment).navigateUp()
        }
    }

    private fun setResultValue(view: TextInputEditText, response: String? = null) {
        when {
            response == null ||
                    response.contains("null") ||
                    response.isNullOrEmpty() -> ((view.parent as FrameLayout).parent as TextInputLayout).visibility =
                View.GONE
            else -> {
                ((view.parent as FrameLayout).parent as TextInputLayout).visibility = View.VISIBLE
                view.setText(response)
            }
        }
    }

    /* STATE FUNCTIONS SECTOR */
    private fun showLoading() {
        linearLayoutProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        linearLayoutProgressBar.visibility = View.GONE
    }

    private fun anttPriceSuccess(response: Response<AnttPriceResponse?>) {
        textViewAnttGeralValue.setText(response.body()?.geral.toString())
        textViewAnttGranelValue.setText(response.body()?.granel.toString())
        textViewAnttNeogranelValue.setText(response.body()?.neogranel.toString())
        textViewAnttFrigorificadaValue.setText(response.body()?.frigorificada.toString())
        textViewAnttPerigosaValue.setText(response.body()?.perigosa.toString())
    }

    private fun anttPriceError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
