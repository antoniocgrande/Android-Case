package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import br.com.antoniocgrande.truckpad_case.R
import br.com.antoniocgrande.truckpad_case.data.request.RouteRequest
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val axis = getString("axis")
            val response = getSerializable("response") as RouteResponse
            val request = getSerializable("request") as RouteRequest

//            //val seconds = response.body().duration
//            val seconds = 100
//            seconds.div(60).div(60).toString() + "h" + (seconds.div(60).rem(60)) + "m"

            setResultValue(textViewOrigemValue, request.literalOriginAddress.toString())
            setResultValue(textViewDestinoValue, request.literalDetinationAddress.toString())
            setResultValue(textViewEixosValue, axis)
            setResultValue(textViewDuracaoValue, response.duration.toString())
            setResultValue(textViewDistanciaValue, response.distance.toString())
            setResultValue(textViewPedagioValue)
            setResultValue(textViewCombustivelNecessarioValue, response.fuelUsage.toString())
            setResultValue(textViewTotalCombustivelValue, response.fuelCost.toString())
            setResultValue(textViewTotalValue, response.totalCost.toString())
        }
    }

    private fun setResultValue(view: TextInputEditText, response: String? = null) {
        when (response) {
            null, "null", "" -> ((view.parent as FrameLayout).parent as TextInputLayout).visibility =
                View.GONE
            else -> {
                ((view.parent as FrameLayout).parent as TextInputLayout).visibility = View.VISIBLE
                view.setText(response)
            }
        }
    }

}
