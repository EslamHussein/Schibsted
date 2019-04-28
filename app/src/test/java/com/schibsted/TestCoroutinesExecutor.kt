package com.schibsted

import com.schibsted.core.executor.ExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestCoroutinesExecutor : ExecutionThread {
    override val mainScheduler: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val ioScheduler: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}