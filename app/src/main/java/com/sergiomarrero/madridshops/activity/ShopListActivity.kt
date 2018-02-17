package com.sergiomarrero.madridshops.activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.adapter.ShopInfoWindowAdapter
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.interactor.getallshops.GetAllShopsInteractor
import com.sergiomarrero.madridshops.domain.interactor.getallshops.GetAllShopsInteractorImpl
import com.sergiomarrero.madridshops.domain.model.Shop
import com.sergiomarrero.madridshops.domain.model.Shops
import com.sergiomarrero.madridshops.fragment.ListFragment
import com.sergiomarrero.madridshops.fragment.MapFragment
import com.sergiomarrero.madridshops.router.Router


class ShopListActivity : AppCompatActivity(),
        ListFragment.OnListItemSelectedListener,
        MapFragment.OnMapItemSelectedListener {

    lateinit var mapFragment: MapFragment
    lateinit var listFragment: ListFragment
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)

        Log.d("App", "ShopListActivity:onCreate")

        mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment
        listFragment = supportFragmentManager.findFragmentById(R.id.listFragment) as ListFragment

        loadShops()
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
            R.id.action_settings -> {
                //Router().navigateFromMainActivityToPicassoActivity(this)

                val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImpl(this)
                getAllShopsInteractor.execute(object: SuccessCompletion<Shops> {
                    override fun successCompletion(shops: Shops) {
                        mapFragment.setShops(shops)
                        listFragment.setShops(shops)
                    }
                }, object: ErrorCompletion {
                    override fun errorCompletion(errorMessage: String) {
                        Toast.makeText(baseContext, "Error loading shops!", Toast.LENGTH_SHORT)
                                .show()
                    }
                })

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onListItemSelected(shop: Shop) {
        Router().navigateToShopDetailActivity(this, shop)
    }

    override fun onMapItemSelected(shop: Shop) {
        Router().navigateToShopDetailActivity(this, shop)
    }


    private fun loadShops() {
        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImpl(this)
        getAllShopsInteractor.execute(object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                mapFragment.setShops(shops)
                listFragment.setShops(shops)
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
