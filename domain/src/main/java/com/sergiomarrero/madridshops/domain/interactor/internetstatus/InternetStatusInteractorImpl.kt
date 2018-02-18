package com.sergiomarrero.madridshops.domain.interactor.internetstatus

import android.content.Context
import android.net.ConnectivityManager
import com.sergiomarrero.madridshops.domain.interactor.EmptyClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure


class InternetStatusInteractorImpl : InternetStatusInteractor {

    override fun execute(context: Context, success: EmptyClosure, error: ErrorClosure) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (connectivityManager != null) {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                success()
            } else {
                error("Connection error")
            }
        } else {
            error("Connection error")
        }
    }
}