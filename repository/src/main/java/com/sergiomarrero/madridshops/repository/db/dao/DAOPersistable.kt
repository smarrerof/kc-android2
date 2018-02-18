package com.sergiomarrero.madridshops.repository.db.dao

import android.database.Cursor


interface DAOReadOperations<T> {
    fun query(id: Long): T
    fun query(type: Int): List<T>
    //fun queryWithCursor(id: Long): Cursor
}

interface DAOWriteOperation<T> {
    fun insert(element: T): Long
    fun update(id: Long, element: T): Long
    fun delete(element: T): Long
    fun delete(id: Long): Long
    fun deleteAll(): Boolean
}

interface DAOPersistable<T>: DAOReadOperations<T>, DAOWriteOperation<T>

