package com.sergiomarrero.madridshops.repository

import com.sergiomarrero.madridshops.repository.model.Entity
import com.sergiomarrero.madridshops.repository.model.ShopEntity


interface Repository {
    fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit)

    fun getAllEntities(type: Int, success: (entities: List<Entity>) -> Unit, error: (errorMessage: String) -> Unit)
}
