package com.example.demo.infra.share.port

import com.example.demo.infra.domain.Report

interface CreateReportPort {
    fun createReport(
        assetName: String,
        volatilityTime: Int = 1,
    ): Report
}