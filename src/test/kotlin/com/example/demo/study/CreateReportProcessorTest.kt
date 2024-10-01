package com.example.demo.study

import com.example.demo.batch.component.CreateReportProcessor
import com.example.demo.infra.adapter.persistence.ReportCachesJpaRepository
import com.example.demo.infra.adapter.persistence.ReportCachesRepositoryImpl
import com.example.demo.infra.share.impl.CustomDateTimeImpl
import com.example.demo.infra.share.port.CreateReportPort
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CreateReportProcessorTest
@Autowired
constructor(
    private val reportCachesJpaRepository: ReportCachesJpaRepository,
    private val createReportPort: CreateReportPort

    ) {
    private val reportCachesRepository: ReportCachesRepositoryImpl =
        ReportCachesRepositoryImpl(
            reportCachesJpaRepository,
            CustomDateTimeImpl(),
        )
    private val processor = CreateReportProcessor(reportCachesRepository, createReportPort)

    @Test
    fun success(){
        val report = processor.process("삼성물산")
        println(report?.getReportBody())
    }
}