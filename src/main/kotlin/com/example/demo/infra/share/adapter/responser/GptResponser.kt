package com.example.demo.infra.share.adapter.responser


import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityApi
import com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatModel
import rkrk.whyprice.config.ApiConfig


class GptResponser(
    private val customDateTime: com.example.demo.infra.share.port.CustomDateTime,
){
    private val perplexityOptions =
        PerplexityChatOptions
            .builder()
            .searchRecencyFilter("week")
            .withModel("llama-3.1-sonar-small-128k-online")
            .withTemperature(0.8F)
            .build()

    private val chatClient: ChatClient =
        ChatClient
            .builder(
                OpenAiChatModel(PerplexityApi(ApiConfig.getPerplexityKey()), perplexityOptions),
            ).build()


    fun createReport(
        assetName: String,
        volatilityTime: Int,
    ): Report {
        val response = fetch(createReportPrompt(assetName, volatilityTime))

        return responseToReport(assetName, response)
    }

    private fun fetch(prompt: Prompt): ChatResponse {
        val response =
            chatClient
                .prompt(prompt)
                .call()
                .chatResponse()
        return response
    }

    // todo 프롬프트에 검색사이트 명시 및 최신 사이트만 검색하게 강제
    // TKG애강 주가 뉴스 inanchor:2024-09-06 inanchor:2024-09-05 inanchor:2024-09-04
    private fun createReportPrompt(
        assetName: String,
        volatilityTime: Int,
    ): Prompt {
        val systemText = "너는 20년 경력의 금융시장 전문가야"
        val systemMessage = SystemMessage(systemText)
        val userTest = """${assetName}의 현재가격과 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유를 검색하고 평가해서 레포트로 써줘"""
        val userMessage = UserMessage(userTest)
        return Prompt(listOf(systemMessage, userMessage))
    }

    private fun createVolatilityPrompt(
        assetName: String,
        volatilityTime: Int,
    ): Prompt {
        val systemText = "너는 20년 경력의 금융시장 전문가야"
        val systemMessage = SystemMessage(systemText)
        val userTest = """${assetName}의 현재가격과 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유를 검색하고 평가해서 o나 x로만 말해줘"""
        userTest.plus(" 변동성이 크면 o,작으면 x로 말하면돼")
        val userMessage = UserMessage(userTest)
        return Prompt(listOf(systemMessage, userMessage))
    }

    private fun responseToReport(
        assetName: String,
        response: ChatResponse,
    ) = Report(assetName, response.result.output.content, customDateTime.getNow())
}
