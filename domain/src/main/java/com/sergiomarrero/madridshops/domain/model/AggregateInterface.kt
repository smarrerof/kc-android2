package com.sergiomarrero.madridshops.domain.model


interface ReadAggregate<T> {
    fun count(): Int
    fun all(): List<T>
    fun get(position: Int): T
}

interface WriteAggregate<T> {
    fun add(element: T)
    fun delete(position: Int)
    fun delete(element: T)
}

interface Aggregate<T>: ReadAggregate<T>, WriteAggregate<T>