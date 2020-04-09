package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.antoniocgrande.truckpad_case.R
import br.com.antoniocgrande.truckpad_case.data.response.RouteResponse
import kotlinx.android.synthetic.main.result_fragment.*

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val response = getSerializable("response") as RouteResponse

            textViewOrigemValue.setText("")
            textViewDestinoValue.setText("")
            textViewEixosValue.setText(getString("axis"))
            textViewDuracaoValue.setText(response.duration.toString())
            textViewDistanciaValue.setText(response.distance.toString())
            textViewPedagioValue.setText("")
            textViewCombustivelNecessarioValue.setText(response.fuelUsage.toString())
            textViewTotalCombustivelValue.setText(response.fuelCost.toString())
            textViewTotalValue.setText(response.tollCost.toString())
        }
    }

}
