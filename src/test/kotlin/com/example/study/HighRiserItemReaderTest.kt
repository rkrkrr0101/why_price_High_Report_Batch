package com.example.study

import com.example.demo.batch.HighRiserItemReader
import com.example.demo.infra.adapter.HighRisersFetcher
import com.example.demo.infra.share.impl.ApiHelperImpl
import org.junit.jupiter.api.Test


class HighRiserItemReaderTest {
    @Test
    fun success(){
        val reader = HighRiserItemReader(HighRisersFetcher(ApiHelperImpl()))
        println(reader.read())
        println(reader.read())
        println(reader.read())
        println(reader.read())
    }

}