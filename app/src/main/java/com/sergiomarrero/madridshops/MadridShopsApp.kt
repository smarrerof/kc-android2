package com.sergiomarrero.madridshops

import android.support.multidex.MultiDexApplication
import android.util.Log
import com.squareup.picasso.Picasso
import java.util.*


class MadridShopsApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Init Picasso
        if (BuildConfig.DEBUG) {
            Picasso.with(this).setIndicatorsEnabled(true)
            Picasso.with(this).isLoggingEnabled = true
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}