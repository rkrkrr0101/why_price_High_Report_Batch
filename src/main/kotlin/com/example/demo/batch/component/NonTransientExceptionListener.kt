package com.example.demo.batch.component

import com.example.demo.infra.domain.Report
import org.slf4j.LoggerFactory
import org.springframework.batch.core.SkipListener

class NonTransientExceptionListener : SkipListener<String, Report> {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onSkipInProcess(
        item: String,
        t: Throwable,
    ) {
        log.warn("반복불가능한 예외발생 item={$item} ")
        super.onSkipInProcess(item, t)
    }
}
