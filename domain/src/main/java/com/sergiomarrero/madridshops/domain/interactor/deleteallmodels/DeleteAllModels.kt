package com.sergiomarrero.madridshops.domain.interactor.deleteallmodels

import com.sergiomarrero.madridshops.domain.interactor.EmptyClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure


interface DeleteAllModels {
    fun execute(success: EmptyClosure, error: ErrorClosure)
}
