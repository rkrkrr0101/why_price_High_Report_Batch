package com.example.demo.infra.adapter.persistence.hotstockreportstaging

import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.CustomDateTime
import org.springframework.stereotype.Repository

@Repository
class HotStockReportStagingRepositoryImpl(
    private val jpaRepository: HotStockReportStagingJpaRepository,
    private val customDateTime: CustomDateTime,
) : HotStockReportStagingRepository {
    override fun saveOrUpdate(report: Report) {
        val staging = jpaRepository.findByReportAssetName(report.getAssetName())
        if (staging == null) {
            val temp = HotStockReportStaging(report)
            jpaRepository.save(temp)
            return
        }
        if (!staging.isValid(customDateTime.getNow())) {
            staging.updateReport(report)
        }
    }
}
