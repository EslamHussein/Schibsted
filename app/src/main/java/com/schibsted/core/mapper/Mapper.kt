package com.schibsted.core.mapper

interface Mapper<F,T> {
    fun map(from:F):T
}