package com.example.demo.batch.component

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class HotStockReportStagingTableInitTasklet (
    private val jdbcTemplate: JdbcTemplate // 생성자 주입 사용
    ) : Tasklet {
        override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
            val tableName = "hot_stock_report_staging"
            val sql = "delete from $tableName"

            jdbcTemplate.execute(sql)
            println("$tableName 테이블이 초기화되었습니다.")

            return RepeatStatus.FINISHED
        }
    }