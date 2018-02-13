package com.sergiomarrero.madridshops.activity

import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.model.Shop
import com.sergiomarrero.madridshops.router.Router.Companion.INTENT_SHOP
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_shop.*
import android.util.DisplayMetrics



class ShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        // Get shop from intent
        val shop = intent.getSerializableExtra(INTENT_SHOP) as Shop

        // Calculate map image dimensions
        val deviceWidth = Resources.getSystem().getDisplayMetrics().widthPixels
        val height = convertDpToPixel(this, 125.0f).toInt()

        // Bind shop
        Picasso
                .with(this)
                .load(shop.image)
                .into(imageView)

        textName.text =  shop.name
        textDescription.text = shop.descriptionEn
        textAddress.text = shop.address
        textOpeningHours.text = shop.openingHoursEn

        Picasso
                .with(this)
                .load("http://maps.googleapis.com/maps/api/staticmap?\n" +
                        "center=${shop.latitude},${shop.longitude}&zoom=17&size=${deviceWidth}x${height}&scale=2")
                .into(imageMapView)
    }

    fun convertDpToPixel(context: Context, dp: Float): Float {
        val resources = context.getResources()
        val metrics = resources.getDisplayMetrics()
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}