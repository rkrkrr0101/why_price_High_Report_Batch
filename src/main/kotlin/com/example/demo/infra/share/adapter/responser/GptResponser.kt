package com.example.demo.infra.share.adapter.responser


import com.example.demo.infra.domain.Report
import com.example.demo.infra.share.port.CreateReportPort
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Component

@Component
class GptResponser(
    private val customDateTime: com.example.demo.infra.share.port.CustomDateTime,
    private val model: ChatModel,
    private val vectorStore: VectorStore,
):CreateReportPort{
    private val chatClient: ChatClient =
        ChatClient
            .builder(model)
            .defaultAdvisors(SimpleLoggerAdvisor())
            .build()


    override fun createReport(
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
//                .advisors(QuestionAnswerAdvisor(
//                    vectorStore,
//                    SearchRequest.Builder().topK(5)
//                        .build())
//                )
                .call()
                .chatResponse()?:throw NotImplementedError()
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
        val userTest = """${assetName}의 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유를 컨텍스트를 사용해서 평가해서 레포트로 써줘"""
        val userMessage = UserMessage(userTest)
        return Prompt(listOf(systemMessage, userMessage))
    }


    private fun responseToReport(
        assetName: String,
        response: ChatResponse,
    ) = Report(assetName, response.result.output.text, customDateTime.getNow())
}
