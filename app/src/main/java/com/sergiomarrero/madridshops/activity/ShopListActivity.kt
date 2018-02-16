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
import com.sergiomarrero.madridshops.router.Router
import kotlinx.android.synthetic.main.activity_shop_list.*


class ShopListActivity : AppCompatActivity(), ListFragment.OnItemSelectedListener {

    lateinit var mapFragment: SupportMapFragment
    lateinit var listFragment: ListFragment
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)

        Log.d("App", "ShopListActivity:onCreate")

        mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        listFragment = supportFragmentManager.findFragmentById(R.id.listFragment) as ListFragment

        setupMap()
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
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                Router().navigateFromMainActivityToPicassoActivity(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemSelected(shop: Shop) {
        Router().navigateToShopDetailActivity(this, shop)
    }


    private fun setupMap() {
        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImpl(this)
        getAllShopsInteractor.execute(object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                initializeMap(shops)
                listFragment.setShops(shops)
            }
        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Toast.makeText(baseContext, "Error loading shops!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun initializeMap(shops: Shops) {
        mapFragment.getMapAsync {
            Log.d("App", "Habemus mapa!")
            centerMapInPosition(it, 40.416775, -3.703790)
            it.uiSettings.isRotateGesturesEnabled = false
            it.uiSettings.isZoomControlsEnabled = true
            showUserPosition(baseContext, it)
            it.setInfoWindowAdapter(ShopInfoWindowAdapter(this))

            map = it

            addAllPins(shops)

            it.setOnInfoWindowClickListener {
                var shop = it.tag as Shop
                Router().navigateToShopDetailActivity(this, shop)
            }
        }
    }

    private fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double) {
        val coordinate = LatLng(latitude, longitude)
        val cameraPosition = CameraPosition.Builder()
                .target(coordinate)
                .zoom(13f)
                .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun showUserPosition(context: Context, map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,  arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 10)

            return
        }
    }

    private fun addAllPins(shops: Shops) {
        shops.shops.forEach {
            addPin(map, it)
        }
    }

    private fun addPin(map: GoogleMap, shop: Shop) {
        val coordinate = LatLng(shop.latitude, shop.longitude)
        val markerOptions = MarkerOptions()
                .position(coordinate)
                .title(shop.name)
        val marker = map.addMarker(markerOptions)
        marker.tag = shop
    }
}