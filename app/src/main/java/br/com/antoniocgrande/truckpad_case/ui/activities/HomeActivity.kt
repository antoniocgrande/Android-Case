package br.com.antoniocgrande.truckpad_case.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.antoniocgrande.truckpad_case.R
import br.com.antoniocgrande.truckpad_case.ui.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }

    }
}
