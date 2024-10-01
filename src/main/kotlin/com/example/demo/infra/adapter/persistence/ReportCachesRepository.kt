package com.example.demo.infra.adapter.persistence

import com.example.demo.infra.domain.Report


interface ReportCachesRepository {
    fun saveOrUpdate(report: Report)

    fun isCacheValid(assetName: String): Boolean

    fun findOne(assetName: String): ReportCache
}
