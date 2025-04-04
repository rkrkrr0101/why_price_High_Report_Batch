package com.example.demo.infra.adapter.persistence

import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.CustomDateTime
import org.springframework.stereotype.Repository

@Repository
class HotStockRepositoryImpl(
    private val jpaRepository: HotStockJpaRepository
) : HotStockRepository {
    override fun findAllStockNames(): List<String> {
        return jpaRepository.findAll().map { it.stockName }
    }
}