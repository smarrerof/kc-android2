package com.sergiomarrero.madridshops.router

import android.content.Intent
import com.sergiomarrero.madridshops.activity.MainActivity
import com.sergiomarrero.madridshops.activity.PicassoActivity
import com.sergiomarrero.madridshops.activity.ShopActivity
import com.sergiomarrero.madridshops.domain.model.Shop


class Router {

    companion object {
        val INTENT_SHOP = "INTENT_SHOP"
    }

    fun navigateFromMainActivityToPicassoActivity(main: MainActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))
    }

    fun navigateToShopActivity(main: MainActivity, shop: Shop) {
        val intent = Intent(main, ShopActivity::class.java)
        intent.putExtra(INTENT_SHOP, shop)
        main.startActivity(intent)
    }
}
