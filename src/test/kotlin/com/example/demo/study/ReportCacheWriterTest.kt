package com.example.demo.study

import com.example.demo.batch.component.ReportCacheWriter
import com.example.demo.infra.adapter.persistence.ReportCachesRepository
import com.example.demo.infra.domain.Report

import org.junit.jupiter.api.Test
import org.springframework.batch.item.Chunk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime


@SpringBootTest
class ReportCacheWriterTest
@Autowired
constructor(
    private val repository: ReportCachesRepository

) {
    private val reportCacheWriter= ReportCacheWriter(repository)
    @Test
    fun success(){
        val reportList = listOf(
            Report("삼성전자", "삼성전자 report", LocalDateTime.now()),
            Report("삼성물산", "삼성물산 report", LocalDateTime.now()),
            Report("lg전자", "lg전자 report", LocalDateTime.now()),
            Report("롯데전자", "롯데전자 report", LocalDateTime.now()),
            Report("코스트코", "코스트코 report", LocalDateTime.now()),
        )
        val chunk = Chunk(reportList)
        reportCacheWriter.write(chunk)

        val findReportCache = repository.findOne("삼성전자")
        println(findReportCache.getMainReport().getReportBody())
    }
}