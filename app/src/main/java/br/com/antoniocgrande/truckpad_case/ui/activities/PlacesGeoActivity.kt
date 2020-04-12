package br.com.antoniocgrande.truckpad_case.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import br.com.antoniocgrande.truckpad_case.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_places_geo.*


class PlacesGeoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_geo)

        setupPlaces()

        idCardView.performClick()

    }

    private fun setupPlaces() {
        val apiKey = "AIzaSyCNKGzOEuoFSgonWsNMJ2MCgsKmJKqSjxs"

        when (!Places.isInitialized()) {
            true -> Places.initialize(this, apiKey)
        }

        val placesClient = Places.createClient(this)

        val autocompleteSupportFragment =
            supportFragmentManager
                .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?

        with(autocompleteSupportFragment) {
            this?.setPlaceFields(
                listOf(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME)
            )
            this?.setHint("Digite local desejado")
            this?.setActivityMode(AutocompleteActivityMode.FULLSCREEN)
            this?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(p0: Place) {
                    val returnIntent = Intent()
                    returnIntent.putExtra("PLACE_SUCCESS", p0 as Parcelable)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }

                override fun onError(p0: Status) {
                    val returnIntent = Intent()
                    returnIntent.putExtra("PLACE_ERROR", p0)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
            })
        }
    }

}
