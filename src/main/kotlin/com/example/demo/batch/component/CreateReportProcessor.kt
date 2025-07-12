package com.example.demo.batch.component

import com.example.demo.infra.adapter.persistence.reportcache.ReportCachesRepository
import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.CreateReportPort
import org.springframework.batch.item.ItemProcessor

open class CreateReportProcessor(
    private val repository: ReportCachesRepository,
    private val createReportPort: CreateReportPort,
) : ItemProcessor<String, Report> {
    override fun process(item: String): Report {
        if (repository.isCacheValid(item)) {
            return repository.findOne(item).getMainReport()
        }
        return createReport(item)
    }

    private fun createReport(stockName: String): Report = createReportPort.createReport(stockName)
}
