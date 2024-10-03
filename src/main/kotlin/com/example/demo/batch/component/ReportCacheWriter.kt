package com.example.demo.batch.component

import com.example.demo.infra.adapter.persistence.ReportCachesRepository
import com.example.demo.infra.domain.Report
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

class ReportCacheWriter(
    private val repository: ReportCachesRepository,
) : ItemWriter<Report> {
    override fun write(chunk: Chunk<out Report>) {
        chunk.forEach { repository.saveOrUpdate(it) }
    }
}
