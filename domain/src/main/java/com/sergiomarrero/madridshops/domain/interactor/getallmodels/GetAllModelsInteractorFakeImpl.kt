package com.sergiomarrero.madridshops.domain.interactor.getallmodels

import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.model.Model
import com.sergiomarrero.madridshops.domain.model.Models
import com.sergiomarrero.madridshops.domain.model.Type


class GetAllModelsInteractorFakeImpl : GetAllModelsInteractor {
    override fun execute(type: Type, success: SuccessCompletion<Models>, error: ErrorCompletion) {
        var allOk = true

        if (allOk) {
            val shops = createFakeListOfModels(type)
            success.successCompletion(shops)
        } else {
            error.errorCompletion("Error while accessing to the repository")
        }
    }

    fun createFakeListOfModels(type: Type): Models {
        val list = ArrayList<Model>()

        for (i in 1..100) {
            val shop = Model(i,
                    type,
                    "Shop${i}",
                    "http://via.placeholder.com/400x150",
                    "http://via.placeholder.com/150x150",
                    "openingHoursEn",
                    "openingHoursEs",
                    "Address${i}",
                    "descriptionEn",
                    "descriptionEs",
                    1.0,
                    1.0)

            list.add(shop)
        }

        val models = Models(list)
        return models
    }
}