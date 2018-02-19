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
import com.sergiomarrero.madridshops.adapter.ModelInfoWindowAdapter
import com.sergiomarrero.madridshops.domain.model.Model
import com.sergiomarrero.madridshops.domain.model.Models


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    companion object {
        val REQUEST_MAP_PERMISSION = 10
    }

    lateinit var root: View
    lateinit var mapFragment: SupportMapFragment
    lateinit var map: GoogleMap
    private var onMapItemSelectedListener: OnMapItemSelectedListener? = null
    private var models: Models? = null

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_MAP_PERMISSION) {
            try {
                map.isMyLocationEnabled = true
            } catch(e: SecurityException) {

            }
        }
    }


    fun setModels(models: Models) {
        this.models = models
        initializeMap(models)
    }


    private fun initializeMap(models: Models) {
        mapFragment.getMapAsync {
            Log.d("App", "Habemus mapa!")
            map = it

            centerMapInPosition(it, 40.416775, -3.703790)
            it.uiSettings.isRotateGesturesEnabled = false
            it.uiSettings.isZoomControlsEnabled = true
            showUserPosition(root.context, it)
            it.setInfoWindowAdapter(ModelInfoWindowAdapter(root.context))

            addAllPins(it, models)

            it.setOnInfoWindowClickListener {
                var model = it.tag as Model
                onMapItemSelectedListener?.onMapItemSelected(model)
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

            requestPermissions(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), REQUEST_MAP_PERMISSION)

            return
        } else {
            map.isMyLocationEnabled = true
        }
    }

    private fun addAllPins(map: GoogleMap, models: Models) {
        models.all().forEach {
            addPin(map, it)
        }
    }

    private fun addPin(map: GoogleMap, model: Model) {
        val coordinate = LatLng(model.latitude, model.longitude)
        val markerOptions = MarkerOptions()
                .position(coordinate)
                .title(model.name)
        val marker = map.addMarker(markerOptions)
        marker.tag = model
    }


    interface OnMapItemSelectedListener {
        fun onMapItemSelected(model: Model)
    }
}// Required empty public constructor
