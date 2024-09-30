package com.example.demo.infra.share.adapter.responser.infrastructure

import org.springframework.ai.openai.api.ApiUtils
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.ai.retry.RetryUtils
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.client.RestClient
import reactor.core.publisher.Flux

class PerplexityApi(
    key: String,
    baseUrl: String = "https://api.perplexity.ai",
) : OpenAiApi(baseUrl, key) {
    private val restClient =
        RestClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeaders(ApiUtils.getJsonContentHeaders(key))
            .defaultStatusHandler(RetryUtils.DEFAULT_RESPONSE_ERROR_HANDLER)
            .build()

    override fun chatCompletionEntity(chatRequest: ChatCompletionRequest?): ResponseEntity<ChatCompletion> {
        Assert.notNull(chatRequest, "The request body can not be null.")
        Assert.isTrue(!chatRequest!!.stream(), "Request must set the steam property to false.")

        return restClient
            .post()
            .uri("/chat/completions", *arrayOfNulls(0))
            .body(
                chatRequest,
            ).retrieve()
            .toEntity(
                ChatCompletion::class.java,
            )
    }

    override fun chatCompletionStream(chatRequest: ChatCompletionRequest?): Flux<ChatCompletionChunk> = throw RuntimeException("지원되지않음")

    override fun <T : Any?> embeddings(embeddingRequest: EmbeddingRequest<T>?): ResponseEntity<EmbeddingList<Embedding>> =
        throw RuntimeException("지원되지않음")
}
