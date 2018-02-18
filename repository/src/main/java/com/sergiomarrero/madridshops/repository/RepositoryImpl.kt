package com.sergiomarrero.madridshops.repository

import android.content.Context
import com.sergiomarrero.madridshops.repository.cache.Cache
import com.sergiomarrero.madridshops.repository.cache.CacheImpl
import com.sergiomarrero.madridshops.repository.model.EntitiesResponse
import com.sergiomarrero.madridshops.repository.model.Entity
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


    override fun getAllEntities(type: Int, success: (entities: List<Entity>) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.getAllEntities(type, { entities ->
            // Read all shops from cache -> return them
            success(entities)
        }, { error ->
            // If no shops in cache -> network
            populateCache(type, success, error)
        })
    }


    private fun populateCache(success: (shops: List<ShopEntity>) -> Unit, error: String) {
        var jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get()!!)
        jsonManager.execute(BuildConfig.API_SHOPS_URL,
        object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                // Parse response
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

    private fun populateCache(type: Int, success: (shops: List<Entity>) -> Unit, error: String) {
        val jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get()!!)
        val url = getTypeUrl(type)
        jsonManager.execute(url,
                object: SuccessCompletion<String> {
                    override fun successCompletion(e: String) {
                        // Parse response
                        val parser = JsonEntitiesParser()
                        val response: EntitiesResponse
                        val entities: ArrayList<Entity> =  arrayListOf()

                        try {
                            response = parser.parse<EntitiesResponse>(e)
                            response.result.forEach {
                                entities.add(Entity(it.id,
                                        it.databaseId,
                                        type,
                                        it.name,
                                        it.image,
                                        it.logoImage,
                                        it.openingHoursEn,
                                        it.openingHoursEs,
                                        it.address,
                                        it.descriptionEn,
                                        it.descriptionEs,
                                        it.latitude,
                                        it.longitude))
                            }

                        } catch (e: Exception) {
                            error("RepositoryImp:populateCache - Error parsing!")
                            return
                        }

                        // Store result in cache
                        cache.saveAllEntities(entities, {
                            success(response.result)
                        }, {
                            error("RepositoryImp:populateCache - Error saving all entities!")
                        })
                    }

                }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {

            }
        })
    }

    private fun getTypeUrl(type: Int): String {
        return when (type) {
            1 -> BuildConfig.API_SHOPS_URL
            2 -> BuildConfig.API_ACTIVITIES_URL
            else -> ""
        }
    }
}