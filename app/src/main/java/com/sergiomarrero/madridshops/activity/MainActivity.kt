package com.sergiomarrero.madridshops.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.interactor.internetstatus.InternetStatusInteractorImpl
import com.sergiomarrero.madridshops.domain.model.Type
import com.sergiomarrero.madridshops.router.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isConnected = false
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageShopList.setOnClickListener {
            Router().navigateToModelListActivity(this, Type.SHOP)
        }
        imageActivityList.setOnClickListener {
            Router().navigateToModelListActivity(this, Type.ANTIVITY)
        }
    }

    override fun onStart() {
        super.onStart()
        checkInternetStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_retry)?.isVisible = !isConnected
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_retry -> {
                checkInternetStatus()
                true
            }
            R.id.action_settings -> {
                Router().navigateToSettingsActivity(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkInternetStatus() {
        InternetStatusInteractorImpl().execute(this, {
            isConnected = true
            configureUI()
        }, {
            isConnected = false
            configureUI()
            Toast.makeText(this, getString(R.string.internet_connection_error), Toast.LENGTH_LONG).show()
        })
    }


    private fun configureUI() {
        if (menu != null) {
            menu?.findItem(R.id.action_retry)?.isVisible = !isConnected
        }
        imageShopList.isEnabled = isConnected
        imageShopList.setImageResource(when (isConnected) {
            true -> R.drawable.button_shops_enabled
            else -> R.drawable.button_shops_disabled
        })
        imageActivityList.isEnabled = isConnected
        imageActivityList.setImageResource(when (isConnected) {
            true -> R.drawable.button_activities_enabled
            else -> R.drawable.button_activities_disabled
        })

    }

}
