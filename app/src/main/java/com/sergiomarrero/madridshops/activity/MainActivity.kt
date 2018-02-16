package com.sergiomarrero.madridshops.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.router.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonShopList.setOnClickListener {
            Router().navigateToShopListActivity(this)
        }
    }
}
