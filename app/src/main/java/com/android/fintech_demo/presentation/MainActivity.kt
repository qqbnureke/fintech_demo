package com.android.fintech_demo.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.fintech_demo.R
import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.presentation.fragments.main.MainFragment
import com.android.fintech_demo.presentation.fragments.my_cards.MyCardsFragment
import com.android.fintech_demo.presentation.utils.NetworkObserverLiveData
import com.google.android.material.snackbar.Snackbar

private const val PARAM_SAVE_INSTANCE = "PARAM_SAVE_INSTANCE"

class MainActivity : AppCompatActivity() {
    private var needToSaveState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState?.getBoolean(PARAM_SAVE_INSTANCE, false) != true) {
            navigateToFragment(MainFragment(), "main_fragment")
            networkObserver()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(PARAM_SAVE_INSTANCE, true)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        needToSaveState = true
    }


    fun onMainCardClicked(cards: List<CardModel>) {
        navigateToFragment(MyCardsFragment.newInstance(cards), "my_cards")
    }

    private fun navigateToFragment(fragment: Fragment, key: String?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flRoot, fragment)
        if (!key.isNullOrEmpty()) transaction.addToBackStack(key)
        transaction.commit()
    }

    fun onCardSelected() {
        onBackPressed()
        val mainFragment = supportFragmentManager.fragments.last()
        if (mainFragment is MainFragment) {
            mainFragment.onCallback()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.fragments.size == 0) finish()
    }

    private fun networkObserver() {
        var snackbar: Snackbar? = null
        NetworkObserverLiveData(this).observe(this) {
            if (!it) {
                snackbar = Snackbar.make(
                    findViewById(R.id.flRoot),
                    getString(R.string.connection_error),
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar?.show()
            } else {
                snackbar?.dismiss()
            }
        }

    }
}