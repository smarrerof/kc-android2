package com.sergiomarrero.madridshops.domain.interactor.deleteallshops

import android.content.Context
import com.sergiomarrero.madridshops.domain.interactor.CodeClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure
import com.sergiomarrero.madridshops.repository.RepositoryImpl
import java.lang.ref.WeakReference

class DeleteAllShopsImpl(context: Context): DeleteAllShops {
    private val context = WeakReference<Context>(context)

    override fun execute(success: CodeClosure, error: ErrorClosure) {
        val repository = RepositoryImpl(context.get()!!)
        repository.deleteAllShops(success, error)
    }
}