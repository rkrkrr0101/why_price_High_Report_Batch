package com.example.demo.infra.adapter.persistence.reportcache

import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.CustomDateTime
import org.springframework.stereotype.Repository


@Repository
class ReportCachesRepositoryImpl(
    private val jpaRepository: ReportCachesJpaRepository,
    private val customDateTime: CustomDateTime,
) : ReportCachesRepository {
    override fun saveOrUpdate(report: Report) {
        val reportCache = jpaRepository.findByReportAssetName(report.getAssetName())
        if (reportCache == null) {
            val temp = ReportCache(report)
            jpaRepository.save(temp)
            return
        }
        if (!reportCache.isValid(customDateTime.getNow())) {
            reportCache.updateReport(report)
        }
    }

    override fun isCacheValid(assetName: String): Boolean {
        val reportCaches = jpaRepository.findByReportAssetName(assetName) ?: return false
        return reportCaches.isValid(customDateTime.getNow())
    }

    override fun findOne(assetName: String): ReportCache =
        jpaRepository.findByReportAssetName(assetName)
            ?: throw IllegalArgumentException("존재하지 않는 캐시입니다.")
}
