package com.sergiomarrero.madridshops.activity

import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.model.Model
import com.sergiomarrero.madridshops.router.Router.Companion.INTENT_MODEL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_shop_detail.*
import android.util.DisplayMetrics
import android.view.MenuItem
import com.sergiomarrero.madridshops.util.TranslationManager


class ShopDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)

        // Get shop from intent
        val shop = intent.getSerializableExtra(INTENT_MODEL) as Model

        // Configure UI
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = shop.name

        // Calculate map image dimensions
        val deviceWidth = Resources.getSystem().getDisplayMetrics().widthPixels
        val height = convertDpToPixel(this, 125.0f).toInt()

        // Bind shop
        Picasso
                .with(this)
                .load(shop.image)
                .into(imageView)

        textName.text =  shop.name
        textDescription.text = TranslationManager.getDescription(shop)
        textAddress.text = shop.address
        textOpeningHours.text = TranslationManager.getOpeningHours(shop)

        Picasso
                .with(this)
                .load("http://maps.googleapis.com/maps/api/staticmap?center=${shop.latitude},${shop.longitude}&zoom=17&size=${deviceWidth}x${height}&scale=2")
                .into(imageMapView)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun convertDpToPixel(context: Context, dp: Float): Float {
        val resources = context.getResources()
        val metrics = resources.getDisplayMetrics()
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
