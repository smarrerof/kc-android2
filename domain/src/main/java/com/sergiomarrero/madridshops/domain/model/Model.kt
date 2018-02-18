package com.sergiomarrero.madridshops.domain.model

import java.io.Serializable


/**
 * Shop: Represents one Shop
 */
data class Model(override val id: Int,
                 override val type: Type,
                override val name: String,

                override val image: String,
                override val logoImage: String,

                override val openingHoursEn: String,
                override val openingHoursEs: String,

                override val address: String,

                override val descriptionEn: String,
                override val descriptionEs: String,

                override val latitude: Double,
                override val longitude: Double): IModel, Serializable {

    init {
        Shops(ArrayList<Shop>())
    }
}


/**
 * Shops:
 */
class Models(val models: MutableList<Model>): Aggregate<Model> {
    override fun count(): Int {
        return models.count()
    }

    override fun all(): List<Model> {
        return models
    }

    override fun get(position: Int): Model {
        return models[position]
    }

    override fun add(element: Model) {
        models.add(element)
    }

    override fun delete(position: Int) {
        models.removeAt(position)
    }

    override fun delete(element: Model) {
        models.remove(element)
    }
}