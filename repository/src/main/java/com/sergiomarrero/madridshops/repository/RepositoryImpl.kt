package com.sergiomarrero.madridshops.repository

import android.content.Context
import com.sergiomarrero.madridshops.repository.cache.Cache
import com.sergiomarrero.madridshops.repository.cache.CacheImpl
import com.sergiomarrero.madridshops.repository.model.ShopEntity
import com.sergiomarrero.madridshops.repository.model.ShopsResponseEntity
import com.sergiomarrero.madridshops.repository.network.GetJsonManager
import com.sergiomarrero.madridshops.repository.network.GetJsonManagerVolleyImpl
import com.sergiomarrero.madridshops.repository.network.json.JsonEntitiesParser
import java.lang.ref.WeakReference

class RepositoryImpl(context: Context): Repository {
    private val weakContext = WeakReference<Context>(context)
    private val cache: Cache = CacheImpl(weakContext.get()!!)

    override fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.getAllShops({ shops ->
            // Read all shops from cache -> return them
            success(shops)
        }, { error ->
            // If no shops in cache -> network
            populateCache(success, error)
        })
    }

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        cache.deleteAllShops(success, error)
    }


    private fun populateCache(success: (shops: List<ShopEntity>) -> Unit, error: String) {
        var jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get()!!)
        jsonManager.execute(BuildConfig.MADRID_SHOPS_SERVER_URL,  object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                val parser = JsonEntitiesParser()
                var response: ShopsResponseEntity
                try {
                    response = parser.parse<ShopsResponseEntity>(e)
                } catch (e: Exception) {
                    error("Error parsing!")
                    return
                }
                // Store result in cache
                cache.saveAllShops(response.result, {
                    success(response.result)
                }, {
                    error("Something was wrong!")
                })
            }

        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {

            }
        })
    }
}