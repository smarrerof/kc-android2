package com.sergiomarrero.madridshops.domain.interactor.getallshops

import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessClosure
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.model.Shop
import com.sergiomarrero.madridshops.domain.model.Shops
import com.sergiomarrero.madridshops.domain.model.Type


class GetAllShopsInteractorFakeImpl: GetAllShopsInteractor {

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        var allOk = true

        // connect to repository

        if (allOk) {
            val shops = createFakeListOfShops()

            success.successCompletion(shops)
        } else {
            error.errorCompletion("Error while accessing to the repository")
        }
    }

    fun execute(success: SuccessClosure, error: ErrorClosure) {
        var allOk = true

        // connect to repository

        if (allOk) {
            val shops = createFakeListOfShops()

            success(shops)
        } else {
            error("Error while accessing to the repository")
        }
    }

    fun createFakeListOfShops(): Shops {
        val list = ArrayList<Shop>()

        for (i in 1..100) {
            val shop = Shop(i,
                    Type.SHOP,
                    "Shop${i}",
                    "http://via.placeholder.com/400x150",
                    "http://via.placeholder.com/150x150",
                    "openingHoursEn",
                    "openingHoursEs",
                    "Address${i}",
                    "descriptionEn",
                    "descriptionEs",
                    10.0,
                    0.0)

            list.add(shop)
        }

        val shops = Shops(list)
        return shops
    }
}
