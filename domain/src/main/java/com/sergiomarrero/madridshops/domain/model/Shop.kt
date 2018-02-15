package com.sergiomarrero.madridshops.domain.model

import java.io.Serializable


/**
 * Shop: Represents one Shop
 */
data class Shop(override val id: Int,
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
class Shops(val shops: MutableList<Shop>): Aggregate<Shop> {
    override fun count(): Int {
        return shops.count()
    }

    override fun all(): List<Shop> {
        return shops
     }

    override fun get(position: Int): Shop {
        return shops[position]
    }

    override fun add(element: Shop) {
        shops.add(element)
    }

    override fun delete(position: Int) {
        shops.removeAt(position)
    }

    override fun delete(element: Shop) {
        shops.remove(element)
    }
}