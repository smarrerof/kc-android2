package com.sergiomarrero.madridshops.domain.model


interface IModel {
    val id: Int
    val type: Type
    val name: String

    val image: String
    val logoImage: String

    val openingHoursEn: String
    val openingHoursEs: String

    val address: String

    val descriptionEn: String
    val descriptionEs: String

    val latitude: Double
    val longitude: Double
}

enum class Type(val value: Int) {
    SHOP(0),
    ANTIVITY(1)
}