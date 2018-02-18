package com.sergiomarrero.madridshops.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.model.Model
import com.sergiomarrero.madridshops.util.TranslationManager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_window_model.view.*


class ModelInfoWindowAdapter(val context: Context): GoogleMap.InfoWindowAdapter  {

    override fun getInfoContents(marker: Marker): View {
        val view = LayoutInflater.from(context).inflate(R.layout.info_window_model, null)

        val model = marker.tag as Model

        Picasso
                .with(context)
                .load(model.logoImage)
                .into(view.imageLogo, MarkerCallback(marker, context, model.logoImage, view.imageLogo))

        view.textName.text = model.name
        view.textOpeningHours.text = TranslationManager.getOpeningHours(model)

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