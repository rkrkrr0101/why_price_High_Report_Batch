package com.example.demo.infra.adapter.persistence.hotstockreport

import org.springframework.data.jpa.repository.JpaRepository

interface HotStockReportJpaRepository : JpaRepository<HotStockReport, Long> {
    fun findByReportAssetName(assetName: String): HotStockReport?
}
