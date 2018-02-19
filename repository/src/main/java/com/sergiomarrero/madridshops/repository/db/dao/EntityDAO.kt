package com.sergiomarrero.madridshops.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import com.sergiomarrero.madridshops.repository.db.DBConstants
import com.sergiomarrero.madridshops.repository.db.DBHelper
import com.sergiomarrero.madridshops.repository.model.Entity
import java.util.ArrayList


class EntityDAO(val dbHelper: DBHelper): DAOPersistable<Entity> {

    private val dbReadOnlyConection: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase

    override fun query(id: Long): Entity {
        val cursor = queryWithCursor(
                "${DBConstants.KEY_SHOP_DATABASE_ID} = ?",
                arrayOf(id.toString()))

        cursor.moveToFirst()

        return entityFromCursor(cursor)!!
    }

    override fun query(type: Int): List<Entity> {
        val cursor = queryWithCursor(
                "${DBConstants.KEY_SHOP_TYPE} = ?",
                arrayOf(type.toString()))

        val queryResult = ArrayList<Entity>()
        while(cursor.moveToNext()) {
            val shopEntity = entityFromCursor(cursor)
            queryResult.add(shopEntity!!)
        }

        return queryResult
    }

    override fun count(type: Int): Long {
        return DatabaseUtils.queryNumEntries(dbReadOnlyConection,
            DBConstants.TABLE_SHOP,
            "${DBConstants.KEY_SHOP_TYPE} = ?",
            arrayOf(type.toString()))
    }

    override fun insert(element: Entity): Long {
        val id = dbReadWriteConnection.insert(DBConstants.TABLE_SHOP,
                null,
                contentValues(element))

        return id
    }

    override fun update(id: Long, element: Entity): Long {
        val rowsAffected = dbReadWriteConnection.update(DBConstants.TABLE_SHOP,
                contentValues(element),
                "${DBConstants.KEY_SHOP_DATABASE_ID} = ?",
                arrayOf(id.toString()))

        return rowsAffected.toLong()
    }

    override fun delete(element: Entity): Long {
        if (element.databaseId > 1) return 0

        return delete(element.databaseId)
    }

    override fun delete(id: Long): Long {
        return dbReadWriteConnection.delete(DBConstants.TABLE_SHOP,
                "${DBConstants.KEY_SHOP_DATABASE_ID} = ?",
                arrayOf(id.toString())).toLong()
    }

    override fun deleteAll(): Boolean {
        return dbReadWriteConnection.delete(DBConstants.TABLE_SHOP,
                null,
                null).toLong() >= 0
    }


    private fun queryWithCursor(selection: String, selectionArgs: Array<String>): Cursor {
        return dbReadOnlyConection.query(DBConstants.TABLE_SHOP,
                DBConstants.ALL_COLUMNS,
                selection,
                selectionArgs,
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID
        )
    }

    private fun contentValues(shopEntity: Entity): ContentValues {
        val content = ContentValues()

        content.put(DBConstants.KEY_SHOP_ID_JSON, shopEntity.id)
        content.put(DBConstants.KEY_SHOP_TYPE, shopEntity.type)
        content.put(DBConstants.KEY_SHOP_NAME, shopEntity.name)

        content.put(DBConstants.KEY_SHOP_IMAGE_URL, shopEntity.image)
        content.put(DBConstants.KEY_SHOP_LOGO_IMAGE_URL, shopEntity.logoImage)

        content.put(DBConstants.KEY_SHOP_OPENING_HOURS_EN , shopEntity.openingHoursEn)
        content.put(DBConstants.KEY_SHOP_OPENING_HOURS_ES, shopEntity.openingHoursEs)

        content.put(DBConstants.KEY_SHOP_ADDRESS, shopEntity.address)

        content.put(DBConstants.KEY_SHOP_DESCRIPTION_EN, shopEntity.descriptionEn)
        content.put(DBConstants.KEY_SHOP_DESCRIPTION_ES, shopEntity.descriptionEs)

        content.put(DBConstants.KEY_SHOP_LATITUDE, shopEntity.latitude)
        content.put(DBConstants.KEY_SHOP_LONGITUDE, shopEntity.longitude)

        return content
    }

    private fun entityFromCursor(cursor: Cursor): Entity? {
        if (cursor.isAfterLast || cursor.isBeforeFirst) return null

        return Entity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_DATABASE_ID)),
                cursor.getInt(cursor.getColumnIndex(DBConstants.KEY_SHOP_TYPE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_NAME)),

                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LOGO_IMAGE_URL)),

                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_OPENING_HOURS_EN)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_OPENING_HOURS_ES)),

                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_ADDRESS)),

                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_DESCRIPTION_EN)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_DESCRIPTION_ES)),

                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LONGITUDE)))
    }
}