package com.sergiomarrero.madridshops.repository.cache

import com.sergiomarrero.madridshops.repository.model.Entity
import com.sergiomarrero.madridshops.repository.model.ShopEntity


internal interface Cache {
    fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun saveAllShops(shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit)

    fun getAllEntities(type: Int, success: (entities: List<Entity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun saveAllEntities(entities: List<Entity>, success: () -> Unit, error: (errorMessage: String) -> Unit)
}
