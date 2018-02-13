package com.sergiomarrero.madridshops.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.model.Shops


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)

        initializeMap()

        return view
    }

    fun setShops(shops: Shops) {
        initializeMap()
    }


    private fun initializeMap() {
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            Log.d("App", "Habemus mapa!")
        }
    }

}// Required empty public constructor
