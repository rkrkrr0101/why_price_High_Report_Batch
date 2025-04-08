package com.example.demo.infra.adapter.persistence.hotstock

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class HotStock(
    stockName: String,
    stockCode: String,
    id:Long = 0
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = id
        protected set

    @Column(name = "stock_name", nullable = false, length = 100)
    var stockName: String = stockName
        protected set

    @Column(name = "stock_code", nullable = false, length = 20)
    var stockCode: String = stockCode
        protected set

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set
}



