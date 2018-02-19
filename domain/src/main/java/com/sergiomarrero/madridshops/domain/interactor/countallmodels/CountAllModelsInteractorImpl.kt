package com.sergiomarrero.madridshops.domain.interactor.countallmodels

import android.content.Context
import android.util.Log
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.model.Type
import com.sergiomarrero.madridshops.repository.Repository
import com.sergiomarrero.madridshops.repository.RepositoryImpl
import java.lang.ref.WeakReference


class CountAllModelsInteractorImpl(context: Context): CountAllModelsInteractor {
    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository = RepositoryImpl(weakContext.get()!!)

    override fun execute(type: Type, success: SuccessCompletion<Long>, error: ErrorCompletion) {
        Log.e("App", "CountAllModelsInteractorImpl:execute")
        repository.countAllEntities(type.value, {
            success.successCompletion(it)
        }, {
            error(it)
        })
    }
}