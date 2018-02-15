package com.sergiomarrero.madridshops.repository

import com.sergiomarrero.madridshops.repository.model.ShopEntity
import com.sergiomarrero.madridshops.repository.model.ShopsResponseEntity
import com.sergiomarrero.madridshops.repository.network.json.JsonEntitiesParser
import com.sergiomarrero.madridshops.repository.util.ReadJsonFile
import org.junit.Assert.*
import org.junit.Test

class JsonParsingTests {
    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_shop_parse_correctly() {
        val json = ReadJsonFile().loadJSONFromAsset("shop.json")
        assertTrue(!json.isEmpty())

        // parsing
        val parser = JsonEntitiesParser()
        val shop = parser.parse<ShopEntity>(json)

        assertEquals("Cortefiel - Preciados", shop.name)
        assertEquals("40.4180563 ", shop.latitude)
    }

    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_shops_parse_correctly() {
        val json = ReadJsonFile().loadJSONFromAsset("shops.json")
        assertTrue(!json.isEmpty())

        // parsing
        val parser = JsonEntitiesParser()
        val responseEntity = parser.parse<ShopsResponseEntity>(json)

        assertEquals(6, responseEntity.result.count())
        assertEquals("Cortefiel - Preciados", responseEntity.result[0].name)
    }
}