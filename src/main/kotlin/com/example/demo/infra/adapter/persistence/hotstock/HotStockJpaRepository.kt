package com.example.demo.infra.adapter.persistence.hotstock

import org.springframework.data.jpa.repository.JpaRepository
interface HotStockJpaRepository : JpaRepository<HotStock, Long>{

}