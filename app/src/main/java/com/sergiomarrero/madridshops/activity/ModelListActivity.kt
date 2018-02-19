package com.sergiomarrero.madridshops.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.interactor.getallmodels.GetAllModelsInteractor
import com.sergiomarrero.madridshops.domain.interactor.getallmodels.GetAllModelsInteractorImpl
import com.sergiomarrero.madridshops.domain.model.*
import com.sergiomarrero.madridshops.fragment.ListFragment
import com.sergiomarrero.madridshops.fragment.MapFragment
import com.sergiomarrero.madridshops.router.Router
import com.sergiomarrero.madridshops.router.Router.Companion.INTENT_TYPE
import kotlinx.android.synthetic.main.activity_model_list.*


class ModelListActivity : AppCompatActivity(),
        ListFragment.OnListItemSelectedListener,
        MapFragment.OnMapItemSelectedListener {

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        READY(1)
    }

    private var type: Type? = null
    lateinit var mapFragment: MapFragment
    lateinit var listFragment: ListFragment
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_list)

        Log.d("App", "ModelListActivity:onCreate")
        viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index

        type = Type.values()[intent.getSerializableExtra(INTENT_TYPE) as Int]
        mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment
        listFragment = supportFragmentManager.findFragmentById(R.id.listFragment) as ListFragment

        loadEntities()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            try {
                map.isMyLocationEnabled = true
            } catch(e: SecurityException) {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_shop_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onListItemSelected(model: Model) {
        Router().navigateToModelDetailActivity(this, model)
    }

    override fun onMapItemSelected(model: Model) {
        Router().navigateToModelDetailActivity(this, model)
    }


    private fun loadEntities() {
        val getAllModelsInteractor: GetAllModelsInteractor = GetAllModelsInteractorImpl(this)
        getAllModelsInteractor.execute(type!!, object: SuccessCompletion<Models> {
            override fun successCompletion(models: Models) {
                Log.d("App", "Models loaded")
                viewSwitcher.displayedChild = VIEW_INDEX.READY.index

                mapFragment.setModels(models)
                listFragment.setModels(models)
            }
        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Toast
                        .makeText(baseContext, "Error loading shops!", Toast.LENGTH_SHORT)
                        .show()
            }
        })
    }
}
