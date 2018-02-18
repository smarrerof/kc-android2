package com.sergiomarrero.madridshops.domain.interactor.getallmodels

import android.content.Context
import android.util.Log
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.model.Model
import com.sergiomarrero.madridshops.domain.model.Models
import com.sergiomarrero.madridshops.domain.model.Type
import com.sergiomarrero.madridshops.repository.Repository
import com.sergiomarrero.madridshops.repository.RepositoryImpl
import com.sergiomarrero.madridshops.repository.model.Entity
import java.lang.ref.WeakReference


class GetAllModelsInteractorImpl(context: Context): GetAllModelsInteractor {
    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository = RepositoryImpl(weakContext.get()!!)

    override fun execute(type: Type, success: SuccessCompletion<Models>, error: ErrorCompletion) {
        Log.e("App", "GetAllModelsInteractorImpl:execute")
        repository.getAllEntities(type.value, {
            val models: Models = entityMapper(it)
            success.successCompletion(models)
        }, {
            error(it)
        })
    }

    private fun entityMapper(list: List<Entity>): Models {
        val tempList = ArrayList<Model>()

        list.forEach({
            try {
                val shop = Model(it.id.toInt(),
                        Type.values()[it.type] ,
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
                Log.e("App", "Error parsing model ${it.name} (id, ${it.id}) with position (${it.latitude}, ${it.longitude})")
            }
        })

        val models = Models(tempList)
        return models
    }

    private fun stringToDouble(value: String): Double {
        val newValue = value.replace(",", "").replace(" ", "")
        return newValue.toDouble()
    }
}