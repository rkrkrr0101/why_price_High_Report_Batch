package com.example.demo.infra.adapter.persistence.hotstockreport

import com.example.demo.infra.domain.Report

interface HotStockReportRepository {
    fun saveOrUpdate(report: Report)
}
