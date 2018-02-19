package com.sergiomarrero.madridshops.repository.cache

import com.sergiomarrero.madridshops.repository.model.Entity


internal interface Cache {
    fun getAllEntities(type: Int, success: (entities: List<Entity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun saveAllEntities(entities: List<Entity>, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllEntities(success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun countAllEntities(type: Int, success: (count: Long) -> Unit, error: (errorMessage: String) -> Unit)
}
