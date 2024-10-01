package com.example.demo.batch

import com.example.demo.infra.adapter.api.HighRisersFetcher
import org.springframework.batch.item.ItemReader

class HighRiserReader(
    private val highRisersFetcher: HighRisersFetcher
): ItemReader<String> {
    private var nextIndex=0
    private var data:List<String> = emptyList()
    override fun read(): String? {
        if(data.isEmpty()){
            data=fetchData()
        }
        return data.getOrNull(nextIndex++)

    }
    private fun fetchData():List<String>{
        return highRisersFetcher.fetch()
    }
}