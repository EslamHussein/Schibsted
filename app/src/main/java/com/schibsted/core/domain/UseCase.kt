package com.schibsted.core.domain

interface UseCase<P, R> {
    suspend fun execute(param: P? = null): R
}