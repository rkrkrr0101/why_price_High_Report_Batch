package com.example.demo.infra.adapter.persistence.hotstockreportstaging

import org.springframework.data.jpa.repository.JpaRepository

interface HotStockReportStagingJpaRepository : JpaRepository<HotStockReportStaging, Long> {
    fun findByReportAssetName(assetName: String): HotStockReportStaging?
}
