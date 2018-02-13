package com.sergiomarrero.madridshops.router

import android.content.Intent
import com.sergiomarrero.madridshops.activity.MainActivity
import com.sergiomarrero.madridshops.activity.PicassoActivity


class Router {
    fun navigateFromMainActivityToPicassoActivity(main: MainActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))
    }
}
