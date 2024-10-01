package com.example.demo.study

import com.example.demo.batch.component.HighRiserReader
import com.example.demo.infra.adapter.api.HighRisersFetcher
import com.example.demo.infra.share.impl.ApiHelperImpl
import org.junit.jupiter.api.Test


class HighRiserReaderTest {
    @Test
    fun success(){
        val reader = HighRiserReader(HighRisersFetcher(ApiHelperImpl()))
        for (i in 1..35) {
            println(reader.read())
        }
    }

}