package com.example.demo.infra.adapter.persistence.hotstockreportstaging

import com.example.demo.infra.adapter.persistence.hotstockreportstaging.HotStockReportStaging
import com.example.demo.infra.domain.Report

interface HotStockReportStagingRepository {
    fun saveOrUpdate(report: Report)
}
