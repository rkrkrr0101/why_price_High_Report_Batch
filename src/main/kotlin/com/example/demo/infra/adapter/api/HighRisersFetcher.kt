package com.example.demo.infra.adapter.api

import com.example.demo.infra.share.port.ApiHelper
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import com.example.demo.infra.config.ApiConfig

class HighRisersFetcher(
    private val apiHelper: ApiHelper,
) {
    fun fetch(): List<String> {
        val restTemplate = apiHelper.createRestTemplate()
        val url = apiHelper.buildUrl(getBaseUrl(), createQueryParams())
        val httpEntity = createHttpEntity()
        val response = apiHelper.fetchApiResponse(restTemplate, url, HttpMethod.GET, httpEntity)

        return extractResponseAsList(response, "hts_kor_isnm")
    }

    private fun createHttpEntity(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("content-type", "application/json; charset=utf-8")
        headers.set("authorization", "Bearer " + KoreanInvTokenFetcher(apiHelper).fetch())
        headers.set("appkey", ApiConfig.getKoreaInvKey())
        headers.set("appsecret", ApiConfig.getKoreaSecretKey())
        headers.set("tr_id", "FHPST01700000")
        headers.set("custtype", "P")

        return HttpEntity<String>("", headers)
    }

    private fun getBaseUrl(): String = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/ranking/fluctuation"

    private fun createQueryParams(): MultiValueMap<String, String> {
        val resMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        resMap["fid_cond_mrkt_div_code"] = "J"
        resMap["fid_cond_scr_div_code"] = "20170"
        resMap["fid_input_iscd"] = "0000"
        resMap["fid_rank_sort_cls_code"] = "0"
        resMap["fid_input_cnt_1"] = "0"
        resMap["fid_prc_cls_code"] = "1"
        resMap["fid_trgt_cls_code"] = "0"
        resMap["fid_trgt_exls_cls_code"] = "0"
        resMap["fid_div_cls_code"] = "0"

        resMap["fid_rsfl_rate1"] = ""
        resMap["fid_rsfl_rate2"] = ""
        resMap["fid_input_price_1"] = ""
        resMap["fid_input_price_2"] = ""
        resMap["fid_vol_cnt"] = ""

        return resMap
    }

    private fun extractResponseAsList(
        response: ResponseEntity<String>,
        key: String,
    ): List<String> {
        val itemNode = extractNodeList(response)
        val resList = mutableListOf<String>()
        for (node in itemNode) {
            resList.add(apiHelper.extractNodeValue(node, key))
        }
        return resList
    }

    private fun extractNodeList(response: ResponseEntity<String>): List<JsonNode> {
        val om = jacksonObjectMapper()
        val readTree = om.readTree(response.body)
        try {
            return readTree
                .get("output")
                .toList()
        } catch (e: IndexOutOfBoundsException) {
            throw NoSuchElementException("${javaClass.name}가 노드 추출에 실패함 ${response.headers.eTag}")
        }
    }
}
