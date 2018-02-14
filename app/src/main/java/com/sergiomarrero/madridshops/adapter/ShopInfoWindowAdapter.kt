package com.sergiomarrero.madridshops.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.model.Shop
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_window_shop.view.*


class ShopInfoWindowAdapter(val context: Context): GoogleMap.InfoWindowAdapter  {

    override fun getInfoContents(marker: Marker): View {
        val view = LayoutInflater.from(context).inflate(R.layout.info_window_shop, null)

        val shop = marker.tag as Shop

        Picasso
                .with(context)
                .load(shop.logoImage)
                .into(view.imageLogo, MarkerCallback(marker, context, shop.logoImage,view.imageLogo))

        view.textName.text = shop.name
        view.textOpeningHours.text = shop.openingHoursEn

        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}

class MarkerCallback(val marker: Marker,
                     val context: Context,
                     val url: String,
                     val imageView: ImageView): Callback {

    override fun onSuccess() {
        if (marker.isInfoWindowShown) {
            marker.hideInfoWindow()

            Picasso
                    .with(context)
                    .load(url)
                    .into(imageView)

            marker.showInfoWindow()
        }
    }

    override fun onError() { Log.d("App", "MarkerCallback:onError - Error loading image") }
}