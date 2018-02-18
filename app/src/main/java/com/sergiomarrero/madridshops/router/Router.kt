package com.sergiomarrero.madridshops.router

import android.content.Intent
import com.sergiomarrero.madridshops.activity.MainActivity
import com.sergiomarrero.madridshops.activity.ModelListActivity
import com.sergiomarrero.madridshops.activity.ModelDetailActivity
import com.sergiomarrero.madridshops.domain.model.Model
import com.sergiomarrero.madridshops.domain.model.Type


class Router {

    companion object {
        val INTENT_TYPE = "INTENT_TYPE"
        val INTENT_MODEL = "INTENT_MODEL"
    }


    fun navigateToModelListActivity(main: MainActivity, type: Type) {
        val intent = Intent(main, ModelListActivity::class.java)
        intent.putExtra(INTENT_TYPE, type.value)
        main.startActivity(intent)
    }

    fun navigateToModelDetailActivity(modelList: ModelListActivity, model: Model) {
        val intent = Intent(modelList, ModelDetailActivity::class.java)
        intent.putExtra(INTENT_MODEL, model)
        modelList.startActivity(intent)
    }
}
