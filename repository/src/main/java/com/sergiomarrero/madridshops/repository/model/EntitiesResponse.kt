package com.sergiomarrero.madridshops.repository.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
internal class EntitiesResponse(
        val result: List<Entity>)
