package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import br.com.antoniocgrande.truckpad_case.R

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

        viewModel.getState.observe(this, Observer { state ->
            when (state) {
                is HomeState.ShowLoading -> showLoading()
                is HomeState.HideLoading -> hideLoading()
                is HomeState.GotoResult -> gotoResult()
            }
        })
    }

    /* STATE FUNCTIONS SECTOR */
    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun gotoResult() = Navigation.findNavController(
        requireActivity(),
        R.id.navHostFragment
    ).navigate(R.id.action_homeFragment_to_resultFragment)

}
