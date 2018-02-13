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
        assertEquals(40.4180563f, shop.latitude, 0.1f)
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

    @Test
    @Throws(Exception::class)
    fun given_invalid_string_containing_json_with_wrong_latitude_it_parses_correctly() {
        val json = ReadJsonFile().loadJSONFromAsset("shopWrongLatitudeLongitude.json")
        assertTrue(!json.isEmpty())

        // parsing
        val parser = JsonEntitiesParser()
        var shop: ShopEntity
        try {
            shop = parser.parse<ShopEntity>(json)
        } catch (e: Exception) {
            shop = ShopEntity(1,1, "", "", 1.0f, 1.0f, "", "", "", "")
        }

        assertNotNull(shop)
        assertEquals("", shop.name)
        assertEquals(1.0f, shop.latitude, 0.1f)
    }
}