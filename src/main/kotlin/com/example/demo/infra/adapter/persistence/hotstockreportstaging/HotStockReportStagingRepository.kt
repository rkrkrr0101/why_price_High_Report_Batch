package com.example.demo.infra.adapter.persistence.hotstockreportstaging

import com.example.demo.infra.domain.Report
import com.example.demo.infra.adapter.persistence.hotstockreportstaging.HotStockReportStaging

interface HotStockReportStagingRepository {
    fun saveOrUpdate(report: Report)

}