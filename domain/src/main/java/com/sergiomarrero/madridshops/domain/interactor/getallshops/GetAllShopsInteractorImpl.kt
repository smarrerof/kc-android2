package com.sergiomarrero.madridshops.domain.interactor.getallshops

import android.content.Context
import android.util.Log
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.model.Shop
import com.sergiomarrero.madridshops.domain.model.Shops
import com.sergiomarrero.madridshops.repository.Repository
import com.sergiomarrero.madridshops.repository.RepositoryImpl
import com.sergiomarrero.madridshops.repository.model.ShopEntity
import java.lang.ref.WeakReference

class GetAllShopsInteractorImpl(context: Context): GetAllShopsInteractor {
    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository = RepositoryImpl(weakContext.get()!!)

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        Log.e("App", "GetAllShopsInteractorImpl:execute")
        repository.getAllShops({
            val shops: Shops = entityMapper(it)
            success.successCompletion(shops)
        }, {
            error(it)
        })
    }

    private fun entityMapper(list: List<ShopEntity>): Shops {
        val tempList = ArrayList<Shop>()

        list.forEach({
            try {
                val shop = Shop(it.id.toInt(),
                        it.name,
                        it.image,
                        it.logoImage,
                        it.openingHoursEn,
                        it.openingHoursEs,
                        it.address,
                        it.descriptionEn,
                        it.descriptionEs,
                        stringToDouble(it.latitude),
                        stringToDouble(it.longitude))
                tempList.add(shop)
            } catch (e: Exception) {
                // Do nothing
                Log.e("App", "Error parsing shop ${it.name} (id, ${it.id}) with position (${it.latitude}, ${it.longitude})")
            }
        })

        val shops = Shops(tempList)
        return shops
    }

    private fun stringToDouble(value: String): Double {
        val newValue = value.replace(",", "").replace(" ", "")
        return newValue.toDouble()
    }
}