package com.sergiomarrero.madridshops.repository

import org.junit.Test

import com.sergiomarrero.madridshops.repository.db.convert
import org.junit.Assert.*


class DBHelperTests {
    @Test @Throws(Exception::class)
    fun given_false_converts_to_0() {
        assertEquals(0, convert(false).toLong())
    }

    fun given_true_converts_to_1() {
        assertEquals(1, convert(true).toLong())
    }
}