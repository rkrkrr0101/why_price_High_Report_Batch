package com.example.demo.infra.share.port

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URI

interface ApiHelper {
    fun createRestTemplate(
        connectTimeout: Long = 3,
        readTimeout: Long = 3,
    ): RestTemplate

    fun buildUrl(
        baseUrl: String,
        queryParams: MultiValueMap<String, String>?,
        encoded: Boolean = false,
    ): URI

    fun fetchApiResponse( // 리팩터링
        restTemplate: RestTemplate,
        url: URI,
        httpMethod: HttpMethod,
        httpEntity: HttpEntity<*>?,
    ): ResponseEntity<String>

    fun extractNodeValue(
        node: JsonNode,
        key: String,
    ): String
}
