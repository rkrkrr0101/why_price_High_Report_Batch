package com.example.demo.batch.component

import org.slf4j.LoggerFactory
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.RetryListener

class TransientExceptionListener : RetryListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun <T : Any?, E : Throwable?> onError(
        context: RetryContext?,
        callback: RetryCallback<T, E>?,
        throwable: Throwable?,
    ) {
        log.warn("반복가능한 예외발생 예외명={${throwable?.message}}")
        super.onError(context, callback, throwable)
    }
}
