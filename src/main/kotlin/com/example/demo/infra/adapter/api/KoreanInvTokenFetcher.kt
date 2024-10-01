package com.example.demo.infra.adapter.api


import com.example.demo.infra.share.port.ApiHelper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import rkrk.whyprice.config.ApiConfig


class KoreanInvTokenFetcher(
    private val apiHelper: ApiHelper,
) {
    fun fetch(): String {
        val restTemplate = apiHelper.createRestTemplate()
        val url = apiHelper.buildUrl(getBaseUrl(), null, false)
        val response = apiHelper.fetchApiResponse(restTemplate, url, HttpMethod.POST, createHttpEntity())
        return extractResponseValue(response, "access_token")
    }

    private fun getBaseUrl(): String = "https://openapi.koreainvestment.com:9443/oauth2/tokenP"

    private fun createHttpEntity(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("content-type", "application/x-www-form-urlencoded")
        return HttpEntity(createRequestBody(), headers)
    }

    private fun createRequestBody(): String {
        val requestBody =
            mapOf(
                "grant_type" to "client_credentials",
                "appkey" to ApiConfig.getKoreaInvKey(),
                "appsecret" to ApiConfig.getKoreaSecretKey(),
            )
        return jacksonObjectMapper().writeValueAsString(requestBody)
    }

    private fun extractResponseValue(
        response: ResponseEntity<String>,
        key: String,
    ): String {
        val om = jacksonObjectMapper()
        val readTree = om.readTree(response.body)
        try {
            return readTree[key].asText()
        } catch (e: IndexOutOfBoundsException) {
            throw NoSuchElementException("${javaClass.name}가 응답 추출에 실패함 ${response.headers.eTag}")
        }
    }
}
