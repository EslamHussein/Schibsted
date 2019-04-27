package com.schibsted.core.executor

import kotlinx.coroutines.CoroutineDispatcher

interface ExecutionThread {
    val mainScheduler: CoroutineDispatcher
    val ioScheduler: CoroutineDispatcher
}