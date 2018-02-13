package com.sergiomarrero.madridshops

import android.support.multidex.MultiDexApplication
import android.util.Log
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.getallshops.GetAllShopsInteractorFakeImpl
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.interactor.deleteallshops.DeleteAllShopsImpl
import com.sergiomarrero.madridshops.domain.interactor.getallshops.GetAllShopsInteractorImpl
import com.sergiomarrero.madridshops.domain.model.Shops


class MadridShopsApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Init code
        Log.d("App", "MadridShopsApp:onCreate")

        val allShopsInteractor = GetAllShopsInteractorImpl(this)
        allShopsInteractor.execute(object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                Log.d("App", "Shops ${shops.count()}")
                shops.shops.forEach { Log.d("App", it.name) }
            }
        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Log.d("App", errorMessage)
            }
        })

        /*DeleteAllShopsImpl(this).execute({
            Log.d("App", "DeleteAllShopsImpl success")
        }, { errorMessage ->
            Log.d("App", errorMessage)
        })*/
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

}