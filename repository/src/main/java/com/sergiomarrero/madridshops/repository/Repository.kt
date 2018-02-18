package com.sergiomarrero.madridshops.repository

import com.sergiomarrero.madridshops.repository.model.Entity


interface Repository {
    fun getAllEntities(type: Int, success: (entities: List<Entity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllEntities(success: () -> Unit, error: (errorMessage: String) -> Unit)
}
