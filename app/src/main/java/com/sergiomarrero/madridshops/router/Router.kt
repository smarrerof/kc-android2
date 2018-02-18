package com.sergiomarrero.madridshops.router

import android.content.Intent
import com.sergiomarrero.madridshops.activity.MainActivity
import com.sergiomarrero.madridshops.activity.ShopListActivity
import com.sergiomarrero.madridshops.activity.PicassoActivity
import com.sergiomarrero.madridshops.activity.ShopDetailActivity
import com.sergiomarrero.madridshops.domain.model.Model


class Router {

    companion object {
        val INTENT_MODEL = "INTENT_MODEL"
    }

    fun navigateFromMainActivityToPicassoActivity(shopList: ShopListActivity) {
        shopList.startActivity(Intent(shopList, PicassoActivity::class.java))
    }

    fun navigateToShopListActivity(main: MainActivity) {
        val intent = Intent(main, ShopListActivity::class.java)
        main.startActivity(intent)
    }


    fun navigateToShopDetailActivity(shopList: ShopListActivity, model: Model) {
        val intent = Intent(shopList, ShopDetailActivity::class.java)
        intent.putExtra(INTENT_MODEL, model)
        shopList.startActivity(intent)
    }
}
