package com.sergiomarrero.madridshops.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.sergiomarrero.madridshops.repository.db.DBConstants
import com.sergiomarrero.madridshops.repository.db.DBHelper
import com.sergiomarrero.madridshops.repository.model.ShopEntity
import java.util.ArrayList


class ShopDAO(val dbHelper: DBHelper): DAOPersistable<ShopEntity> {

    private val dbReadOnlyConection: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase

    override fun query(id: Long): ShopEntity {
        val cursor = queryWithCursor(id)

        cursor.moveToFirst()

        return entityFromCursor(cursor)!!
    }

    override fun query(): List<ShopEntity> {
        val queryResult = ArrayList<ShopEntity>()

        val cursor = dbReadOnlyConection.query(DBConstants.TABLE_SHOP,
                DBConstants.ALL_COLUMNS,
                null,
                null,
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID)

        while(cursor.moveToNext()) {
            val shopEntity = entityFromCursor(cursor)
            queryResult.add(shopEntity!!)
        }

        return queryResult
    }

    override fun queryWithCursor(id: Long): Cursor {
        return dbReadOnlyConection.query(DBConstants.TABLE_SHOP,
            DBConstants.ALL_COLUMNS,
            "${DBConstants.KEY_SHOP_DATABASE_ID} = ?",
            arrayOf(id.toString()),
            "",
            "",
            DBConstants.KEY_SHOP_DATABASE_ID
        )
    }

    override fun insert(element: ShopEntity): Long {
        var id: Long = 0

        id = dbReadWriteConnection.insert(DBConstants.TABLE_SHOP,
            null,
            contentValues(element))

        return id
    }

    override fun update(id: Long, element: ShopEntity): Long {
        val rowsAffected = dbReadWriteConnection.update(DBConstants.TABLE_SHOP,
                contentValues(element),
                "${DBConstants.KEY_SHOP_DATABASE_ID} = ?",
                arrayOf(id.toString()))

        return rowsAffected.toLong()
    }

    override fun delete(element: ShopEntity): Long {
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

    fun contentValues(shopEntity: ShopEntity): ContentValues {
        val content = ContentValues()

        content.put(DBConstants.KEY_SHOP_ID_JSON, shopEntity.id)
        content.put(DBConstants.KEY_SHOP_NAME , shopEntity.name)
        content.put(DBConstants.KEY_SHOP_DESCRIPTION , shopEntity.description)
        content.put(DBConstants.KEY_SHOP_LATITUDE , shopEntity.latitude)
        content.put(DBConstants.KEY_SHOP_LONGITUDE , shopEntity.longitude)
        content.put(DBConstants.KEY_SHOP_IMAGE_URL , shopEntity.img)
        content.put(DBConstants.KEY_SHOP_LOGO_IMAGE_URL , shopEntity.logo)
        content.put(DBConstants.KEY_SHOP_OPENING_HOURS , shopEntity.openingHours)
        content.put(DBConstants.KEY_SHOP_ADDRESS , shopEntity.address)

        return content
    }

    fun entityFromCursor(cursor: Cursor): ShopEntity? {
        if (cursor.isAfterLast || cursor.isBeforeFirst) return null

        return ShopEntity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_OPENING_HOURS)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_ADDRESS)))
    }

}