package com.example.demo.batch

import com.example.demo.batch.component.CreateReportProcessor
import com.example.demo.batch.component.HighRiserReader
import com.example.demo.batch.component.NonTransientExceptionListener
import com.example.demo.batch.component.ReportCacheWriter
import com.example.demo.infra.adapter.api.HighRisersFetcher
import com.example.demo.infra.adapter.persistence.ReportCachesRepository
import com.example.demo.infra.adapter.persistence.HotStockRepository
import com.example.demo.infra.adapter.db.HotStockFetcher
import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.ApiHelper
import com.example.demo.infra.share.port.CreateReportPort
import org.springframework.ai.retry.NonTransientAiException
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class HighRiserConfig {
    @Bean
    fun highRiserJob(
        jobRepository: JobRepository,
        highRiserStep: Step,
    ): Job =
        JobBuilder("highRiserRankJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(highRiserStep)
            .build()

    @JobScope
    @Bean
    fun highRiserStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        highRiserReader: HighRiserReader,
        createReportProcessor: CreateReportProcessor,
        reportCacheWriter: ReportCacheWriter,
    ): Step =
        StepBuilder("highRiserStep", jobRepository)
            .chunk<String, Report>(3, transactionManager)
            .reader(highRiserReader)
            .processor(createReportProcessor)
            .writer(reportCacheWriter)
            .faultTolerant()
            .skip(NonTransientAiException::class.java)
            .skipLimit(3)
            .listener(NonTransientExceptionListener::class.java)
            .build()

    @StepScope
    @Bean
    fun highRiserReader(hotStockFetcher: HotStockFetcher): HighRiserReader = HighRiserReader(hotStockFetcher)

    @Bean
    fun hotStockFetcher(hotStockRepository: HotStockRepository): HotStockFetcher = HotStockFetcher(hotStockRepository)

    @StepScope
    @Bean
    fun createReportProcessor(
        reportCachesRepository: ReportCachesRepository,
        createReportPort: CreateReportPort,
    ): CreateReportProcessor = CreateReportProcessor(reportCachesRepository, createReportPort)

    @StepScope
    @Bean
    fun reportCacheWriter(reportCachesRepository: ReportCachesRepository): ReportCacheWriter = ReportCacheWriter(reportCachesRepository)
}
