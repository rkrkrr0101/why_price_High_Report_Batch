package com.example.demo.infra.adapter.persistence.hotstock

interface HotStockRepository {
    fun findAllStockNames(): List<String>
}
