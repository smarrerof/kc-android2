package com.sergiomarrero.madridshops.fragment


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.adapter.ShopInfoWindowAdapter
import com.sergiomarrero.madridshops.domain.model.Shop
import com.sergiomarrero.madridshops.domain.model.Shops
import kotlinx.android.synthetic.main.fragment_map.*


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    lateinit var root: View
    lateinit var mapFragment: SupportMapFragment
    private var onMapItemSelectedListener: OnMapItemSelectedListener? = null
    private var shops: Shops? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root = inflater!!.inflate(R.layout.fragment_map, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnMapItemSelectedListener) {
            onMapItemSelectedListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onMapItemSelectedListener = null
    }


    fun setShops(shops: Shops) {
        this.shops = shops
        initializeMap(shops)
    }


    private fun initializeMap(shops: Shops) {
        mapFragment.getMapAsync {
            Log.d("App", "Habemus mapa!")
            centerMapInPosition(it, 40.416775, -3.703790)
            it.uiSettings.isRotateGesturesEnabled = false
            it.uiSettings.isZoomControlsEnabled = true
            showUserPosition(root.context, it)
            it.setInfoWindowAdapter(ShopInfoWindowAdapter(root.context))

            addAllPins(it, shops)

            it.setOnInfoWindowClickListener {
                var shop = it.tag as Shop
                onMapItemSelectedListener?.onMapItemSelected(shop)
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

            ActivityCompat.requestPermissions(activity,  arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 10)

            return
        }
    }

    private fun addAllPins(map: GoogleMap, shops: Shops) {
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


    interface OnMapItemSelectedListener {
        fun onMapItemSelected(shop: Shop)
    }
}// Required empty public constructor
