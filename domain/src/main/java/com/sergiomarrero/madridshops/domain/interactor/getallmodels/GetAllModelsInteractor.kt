package com.sergiomarrero.madridshops.domain.interactor.getallmodels

import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.model.Models
import com.sergiomarrero.madridshops.domain.model.Type


interface GetAllModelsInteractor {
    fun execute(type: Type, success: SuccessCompletion<Models>, error: ErrorCompletion)
}
