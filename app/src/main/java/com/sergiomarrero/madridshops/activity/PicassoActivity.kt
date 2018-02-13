package com.sergiomarrero.madridshops.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sergiomarrero.madridshops.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_picasso.*

class PicassoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picasso)

        Picasso.with(this).setIndicatorsEnabled(true)
        Picasso.with(this).isLoggingEnabled = true

        Picasso
                .with(this)
                .load("http://coldtruethoughts.com/wp-content/uploads/2015/07/3100-1.jpg")
                .placeholder(android.R.drawable.ic_delete)
                .into(image1)

        Picasso
                .with(this)
                .load("http://fbcoverstreet.com/content/8061732vtiy67xYh8U61zkR0Cz8p9G45539c15i11828Q14k2304165XV3K93Zt3.jpg")
                .placeholder(android.R.drawable.ic_delete)
                .into(image2)

        Picasso
                .with(this)
                .load("http://www.f-covers.com/cover/suits-cast-facebook-cover-timeline-banner-for-fb.jpg")
                .placeholder(android.R.drawable.ic_delete)
                .into(image3)
    }
}
