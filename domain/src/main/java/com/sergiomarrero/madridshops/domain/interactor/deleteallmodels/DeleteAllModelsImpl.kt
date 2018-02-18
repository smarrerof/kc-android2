package com.sergiomarrero.madridshops.domain.interactor.deleteallmodels

import android.content.Context
import com.sergiomarrero.madridshops.domain.interactor.EmptyClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure
import com.sergiomarrero.madridshops.repository.RepositoryImpl
import java.lang.ref.WeakReference


class DeleteAllModelsImpl(context: Context): DeleteAllModels {
    private val context = WeakReference<Context>(context)

    override fun execute(success: EmptyClosure, error: ErrorClosure) {
        val repository = RepositoryImpl(context.get()!!)
        repository.deleteAllEntities(success, error)
    }
}