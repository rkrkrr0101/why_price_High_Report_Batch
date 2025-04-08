package com.example.demo.infra.adapter.persistence.hotstockreport

import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.CustomDateTime
import org.springframework.stereotype.Repository

@Repository
class HotStockReportRepositoryImpl(
    private val jpaRepository: HotStockReportJpaRepository,
    private val customDateTime: CustomDateTime,
) : HotStockReportRepository {

    override fun saveOrUpdate(report: Report) {
        val hotStockReport = jpaRepository.findByReportAssetName(report.getAssetName())
        if (hotStockReport == null) {
            val temp = HotStockReport(report)
            jpaRepository.save(temp)
            return
        }
        if (!hotStockReport.isValid(customDateTime.getNow())) {
            hotStockReport.updateReport(report)
        }
    }
}