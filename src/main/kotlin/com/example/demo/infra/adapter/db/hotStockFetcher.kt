package com.example.demo.infra.adapter.db

import com.example.demo.infra.adapter.persistence.HotStockRepository

class HotStockFetcher(
   private val hotStockRepository:HotStockRepository 
) {
    fun fetch():List<String>{
        hotStockRepository.findAllStockNames()
        return hotStockRepository.findAllStockNames()
    }

}