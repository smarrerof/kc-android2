package com.sergiomarrero.madridshops.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.sergiomarrero.madridshops.repository.db.buildDBHelper
import com.sergiomarrero.madridshops.repository.db.dao.ShopDAO
import com.sergiomarrero.madridshops.repository.model.ShopEntity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ShopDAOTests {
    val appContext = InstrumentationRegistry.getTargetContext()
    val dbHelper = buildDBHelper(appContext,
        "mydb.sqlite",
        1)

    @Test
    @Throws(Exception::class)
    fun given_valid_shopentity_it_gets_inserted_correctly() {

        val shopEntity = ShopEntity(1,
                1,
                "Shop 1",
                "Description 1",
                1.0f,
                1.0f,
                "",
                "",
                "Opening hours 1",
                "Address 1")

        val shopEntityDAO = ShopDAO(dbHelper)
        val id = shopEntityDAO.insert(shopEntity)

        assertTrue(id > 0)
    }

    // TODO Convert into a valid test
    private fun test() {
        // Never ever!!!
        val dbHelper = buildDBHelper(appContext,
                "mydb.sqlite",
                1)

        val shopEntityDAO = ShopDAO(dbHelper)

        val deleteAll = shopEntityDAO.deleteAll()

        val shopEntity1 = ShopEntity(1,
                1,
                "Shop 1",
                "Description 1",
                1.0f,
                1.0f,
                "",
                "",
                "Opening hours 1",
                "Address 1")

        val shopEntity2 = ShopEntity(1,
                1,
                "Shop 1",
                "Description 2",
                1.0f,
                1.0f,
                "",
                "",
                "Opening hours 2",
                "Address 2")

        val id1 = shopEntityDAO.insert(shopEntity1)
        val id2 = shopEntityDAO.insert(shopEntity2)

        shopEntityDAO.query().forEach {
            Log.d("App", it.name)
        }
    }
}
