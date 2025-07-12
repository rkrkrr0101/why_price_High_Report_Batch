package com.example.demo.infra.adapter.persistence.hotstock

import org.springframework.stereotype.Repository

@Repository
class HotStockRepositoryImpl(
    private val jpaRepository: HotStockJpaRepository,
) : HotStockRepository {
    override fun findAllStockNames(): List<String> = jpaRepository.findAll().map { it.stockName }
}
