package br.com.antoniocgrande.truckpad_case.ui.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import br.com.antoniocgrande.truckpad_case.R
import kotlinx.android.synthetic.main.home_fragment.*

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
                is HomeState.GotoResult -> gotoResult()
            }
        })
    }

    private fun setupListeners() {
        clearText(imageViewClearOrigem, textInputEditTextOrigem)
        clearText(imageViewClearDestino, textInputEditTextDestino)
        clearText(imageViewClearConsumo, textInputEditTextConsumo, "7,5")
        clearText(imageViewClearPreco, textInputEditTextPreco, "3,4")
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

    private fun gotoResult() = Navigation.findNavController(
        requireActivity(),
        R.id.navHostFragment
    ).navigate(R.id.action_homeFragment_to_resultFragment)

}
