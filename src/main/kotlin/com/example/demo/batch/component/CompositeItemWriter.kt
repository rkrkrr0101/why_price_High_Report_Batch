package com.example.demo.batch.component

import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

open class  CompositeItemWriter<T>: ItemWriter<T>{
    private val delegates:MutableList<ItemWriter<T>> = arrayListOf()
    override fun write(chunk: Chunk<out T?>) {
        delegates.forEach { it.write(chunk) }
    }
    fun addWriter(itemWriter: ItemWriter<T>){
        delegates.add(itemWriter)
    }
}