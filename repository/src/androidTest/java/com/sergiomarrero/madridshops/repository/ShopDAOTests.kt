package com.sergiomarrero.madridshops.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sergiomarrero.madridshops.repository.db.buildDBHelper
import com.sergiomarrero.madridshops.repository.db.dao.EntityDAO
import com.sergiomarrero.madridshops.repository.model.Entity
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

        val shopEntity = Entity(1,
                1,
                1,
                "name",
                "image",
                "logo image",
                "opening hours en",
                "opening hours es",
                "address",
                "description en",
                "description es",
                "1.0",
                "1.0")

        val shopEntityDAO = EntityDAO(dbHelper)
        val id = shopEntityDAO.insert(shopEntity)

        assertTrue(id > 0)
    }
}
