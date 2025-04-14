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
   // private val vectorStore: VectorStore,
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
        val userTest = createReportCotPrompt(assetName, volatilityTime)
        val userMessage = UserMessage(userTest)
        return Prompt(listOf(systemMessage, userMessage))
    }


    private fun responseToReport(
        assetName: String,
        response: ChatResponse,
    ) = Report(assetName, response.result.output.text, customDateTime.getNow())

    private fun createReportCotPrompt(
        assetName: String,
        volatilityTime: Int,
    ): String {
        val builder = StringBuilder()
        builder.append("""${assetName}의 최근 ${volatilityTime}시간동안의 변동성과 변동성의 이유에 대한 상세 레포트를 작성해주세요""")
        builder.append("다음 단계에 따라 체계적으로 분석해주길 바랍니다")
        builder.append(
            "1.시장 환경:\n" +
                    "1-1.오늘의 코스피 지수 동향과 $assetName 주가와의 연관성을 설명해주세요.\n" +
                    "1-2.동종 업계 다른 기업들의 주가 동향과 비교 분석해주세요.",
        )
        builder.append(
            "2.뉴스 및 이벤트:\n" +
                    "2-1.오늘 ${assetName}와 관련된 주요 뉴스나 이벤트가 있었는지 확인하고 영향을 분석해주세요.\n" +
                    "2-2.산업 전반에 영향을 줄 수 있는 거시경제 뉴스가 있었는지 확인해주세요.",
        )
        builder.append("3.투자자 동향: 오늘의 기관, 외국인, 개인 투자자별 순매수 동향을 분석해주세요.")
        builder.append("4.변동성 요인 종합: 위의 분석을 종합하여 오늘의 높은 변동성의 주요 원인을 제시해주세요.")
        builder.append("5.단기 전망: 오늘의 변동성을 고려할 때, 향후 수일간의 주가 흐름에 대한 전망을 제시해주세요.")
        builder.append("각 단계에서 관련 데이터와 근거를 제시하고, 논리적인 추론 과정을 보여주세요. 또한, 각 요소가 오늘의 주가 변동성에 어떻게 영향을 미쳤는지 설명해주시기 바랍니다.")
        return builder.toString()
    }
}
