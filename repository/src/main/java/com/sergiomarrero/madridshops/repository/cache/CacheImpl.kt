package com.sergiomarrero.madridshops.repository.cache

import android.content.Context
import com.sergiomarrero.madridshops.repository.db.DBHelper
import com.sergiomarrero.madridshops.repository.db.buildDBHelper
import com.sergiomarrero.madridshops.repository.db.dao.EntityDAO
import com.sergiomarrero.madridshops.repository.model.Entity
import com.sergiomarrero.madridshops.repository.thread.DispatchOnMainThread
import java.lang.ref.WeakReference


class CacheImpl(context: Context): Cache {
    private val context = WeakReference<Context>(context)


    override fun getAllEntities(type: Int, success: (entities: List<Entity>) -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            var result = EntityDAO(cacheDBHelper()).query(type)
            DispatchOnMainThread(Runnable {
                if (result.count() > 0) {
                    success(result)
                } else {
                    error("No shops")
                }
            })
        }).run()
    }

    override fun saveAllEntities(entities: List<Entity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            try {
                entities.forEach { EntityDAO(cacheDBHelper()).insert(it) }
                DispatchOnMainThread(Runnable {
                    success()
                })
            } catch (e: Exception) {
                error("Error")
            }
        }).run()
    }

    override fun deleteAllEntities(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            var result = EntityDAO(cacheDBHelper()).deleteAll()
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