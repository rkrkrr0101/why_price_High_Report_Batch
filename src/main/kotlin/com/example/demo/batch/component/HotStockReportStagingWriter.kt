package com.example.demo.batch.component

import com.example.demo.infra.adapter.persistence.hotstockreportstaging.HotStockReportStagingRepository

import com.example.demo.infra.domain.Report
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

open class HotStockReportStagingWriter (
    private val repository: HotStockReportStagingRepository,
    ) : ItemWriter<Report> {
        override fun write(chunk: Chunk<out Report>) {
            chunk.forEach { repository.saveOrUpdate(it) }
        }
    }