package com.example.demo.batch.component

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class SwapDataTasklet(
    private val jdbcTemplate: JdbcTemplate, // 생성자 주입 사용
) : Tasklet {
    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext,
    ): RepeatStatus {
        val targetTable = "hot_stock_report"
        val stagingTable = "hot_stock_report_staging"

        val deleteTargetSql = "delete from $targetTable"
        val deleteStagingSql = "delete from $stagingTable"
        val insertSelectSql = "INSERT INTO $targetTable SELECT * FROM $stagingTable"

        jdbcTemplate.execute(deleteTargetSql)
        jdbcTemplate.execute(insertSelectSql)
        jdbcTemplate.execute(deleteStagingSql)

        println("데이터 교체 완료")

        return RepeatStatus.FINISHED
    }
}
