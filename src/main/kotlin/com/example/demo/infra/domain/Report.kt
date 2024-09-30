package com.example.demo.infra.domain

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class Report(
    private val assetName: String,
    private val report: String,
    private val createDateTime: LocalDateTime,
) {
    fun getAssetName(): String = assetName

    fun getReportBody(): String = report

    fun getCreateTime(): LocalDateTime = createDateTime
}
