package com.example.demo.infra.adapter.persistence

interface HotStockRepository {
    fun findAllStockNames(): List<String>
}