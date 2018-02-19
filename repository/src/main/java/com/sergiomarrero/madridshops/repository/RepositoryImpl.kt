package com.sergiomarrero.madridshops.repository

import android.content.Context
import com.sergiomarrero.madridshops.repository.cache.Cache
import com.sergiomarrero.madridshops.repository.cache.CacheImpl
import com.sergiomarrero.madridshops.repository.model.EntitiesResponse
import com.sergiomarrero.madridshops.repository.model.Entity
import com.sergiomarrero.madridshops.repository.network.GetJsonManager
import com.sergiomarrero.madridshops.repository.network.GetJsonManagerVolleyImpl
import com.sergiomarrero.madridshops.repository.network.json.JsonEntitiesParser
import java.lang.ref.WeakReference

class RepositoryImpl(context: Context): Repository {
    private val weakContext = WeakReference<Context>(context)
    private val cache: Cache = CacheImpl(weakContext.get()!!)


    override fun getAllEntities(type: Int, success: (entities: List<Entity>) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.getAllEntities(type, { entities ->
            // Read all shops from cache -> return them
            success(entities)
        }, {
            // If no shops in cache -> network
            populateCache(type, success, it)
        })
    }

    override fun deleteAllEntities(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        cache.deleteAllEntities(success, error)
    }

    override fun countAllEntities(type: Int, success: (count: Long) -> Unit, error: (errorMessage: String) -> Unit) {
        cache.countAllEntities(type, success, error)
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
            0 -> BuildConfig.API_SHOPS_URL
            1 -> BuildConfig.API_ACTIVITIES_URL
            else -> ""
        }
    }
}