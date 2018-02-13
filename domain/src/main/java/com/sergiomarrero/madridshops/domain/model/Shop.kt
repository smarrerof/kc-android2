package com.sergiomarrero.madridshops.domain.model


/**
 * Shop: Represents one Shop
 */
data class Shop(val id: Int, val name: String, val address: String) {
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