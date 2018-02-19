package com.sergiomarrero.madridshops.domain.interactor.countallmodels

import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.model.Type


interface CountAllModelsInteractor {
    fun execute(type: Type, success: SuccessCompletion<Long>, error: ErrorCompletion)
}