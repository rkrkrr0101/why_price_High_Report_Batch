package com.example.demo.batch.component

import com.example.demo.infra.adapter.api.HighRisersFetcher
import com.example.demo.infra.adapter.persistence.ReportCachesRepository
import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.ApiHelper
import com.example.demo.infra.share.port.CreateReportPort
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
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
        JobBuilder("highRiserJob", jobRepository)
            .start(highRiserStep)
            .build()

    @JobScope
    @Bean
    fun highRiserStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        highRiserReader: ItemReader<String>,
        createReportProcessor: ItemProcessor<String, Report>,
        reportCacheWriter: ItemWriter<Report>,
    ): Step =
        StepBuilder("highRiserStep", jobRepository)
            .chunk<String, Report>(3, transactionManager)
            .reader(highRiserReader)
            .processor(createReportProcessor)
            .writer(reportCacheWriter)
            .build()

    @StepScope
    @Bean
    fun highRiserReader(highRisersFetcher: HighRisersFetcher): ItemReader<String> = HighRiserReader(highRisersFetcher)

    @Bean
    fun highRisersFetcher(apiHelper: ApiHelper): HighRisersFetcher = HighRisersFetcher(apiHelper)

    @StepScope
    @Bean
    fun createReportProcessor(
        reportCachesRepository: ReportCachesRepository,
        createReportPort: CreateReportPort,
    ): ItemProcessor<String, Report> = CreateReportProcessor(reportCachesRepository, createReportPort)

    @StepScope
    @Bean
    fun reportCacheWriter(reportCachesRepository: ReportCachesRepository): ItemWriter<Report> = ReportCacheWriter(reportCachesRepository)
}
