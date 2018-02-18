package com.sergiomarrero.madridshops.repository.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class Entity(
        val id: Long,
        val databaseId: Long,
        val type: Int,
        val name: String,

        @JsonProperty("img") val image: String,
        @JsonProperty("logo_img") val logoImage: String,

        @JsonProperty("opening_hours_en") val openingHoursEn: String,
        @JsonProperty("opening_hours_es") val openingHoursEs: String,

        val address: String,

        @JsonProperty("description_en") val descriptionEn: String,
        @JsonProperty("description_es") val descriptionEs: String,

        @JsonProperty("gps_lat") val latitude: String,
        @JsonProperty("gps_lon") val longitude: String
)
