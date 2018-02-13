package com.sergiomarrero.madridshops.repository.cache

import android.content.Context
import com.sergiomarrero.madridshops.repository.db.DBHelper
import com.sergiomarrero.madridshops.repository.db.buildDBHelper
import com.sergiomarrero.madridshops.repository.db.dao.ShopDAO
import com.sergiomarrero.madridshops.repository.model.ShopEntity
import com.sergiomarrero.madridshops.repository.thread.DispatchOnMainThread
import java.lang.ref.WeakReference


class CacheImpl(context: Context): Cache {
    private val context = WeakReference<Context>(context)

    override fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            var result = ShopDAO(cacheDBHelper()).query()
            DispatchOnMainThread(Runnable {
                if (result.count() > 0) {
                    success(result)
                } else {
                    error("No shops")
                }
            })
        }).run()
    }

    override fun saveAllShops(shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            try {
                shops.forEach { ShopDAO(cacheDBHelper()).insert(it) }
                DispatchOnMainThread(Runnable {
                    success()
                })
            } catch (e: Exception) {
                error("Error")
            }
        }).run()
    }

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            var result = ShopDAO(cacheDBHelper()).deleteAll()
            DispatchOnMainThread(Runnable {
                if (result) {
                    success()
                } else {
                    error("Error deleting")
                }
            })
        }).run()
    }

    private fun cacheDBHelper(): DBHelper {
        return buildDBHelper(context.get() !!, "MadridShops.sqlite", 1)
    }
}