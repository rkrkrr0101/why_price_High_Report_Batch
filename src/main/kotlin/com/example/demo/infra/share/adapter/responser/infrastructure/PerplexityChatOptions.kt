package com.example.demo.infra.share.adapter.responser.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.ai.openai.OpenAiChatOptions

// https://docs.perplexity.ai/api-reference/chat-completions
// todo 테스트생성
@JsonInclude(JsonInclude.Include.NON_NULL)
class PerplexityChatOptions : OpenAiChatOptions() {
    @JsonProperty("search_domain_filter")
    var searchDomainFilter: String? = null

    @JsonProperty("search_recency_filter")
    var searchRecencyFilter: String? = null

    companion object {
        @JvmStatic
        fun builder(): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder =
            com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder()
    }

    override fun equals(other: Any?): Boolean {
        val baseEquals = super.equals(other)
        if (!baseEquals) {
            return false
        }
        val castOther = other as com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions
        if (this.searchDomainFilter != castOther.searchDomainFilter) {
            return false
        }
        if (this.searchRecencyFilter != castOther.searchRecencyFilter) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (searchDomainFilter.hashCode())
        result = 31 * result + (searchRecencyFilter.hashCode())
        return result
    }

    class PerplexityBuilder {
        private val perplexityChatOptions =
            com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions()

        fun searchDomainFilter(searchDomainFilter: String): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.searchDomainFilter = searchDomainFilter
            return this
        }

        fun searchRecencyFilter(searchRecencyFilter: String): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.searchRecencyFilter = searchRecencyFilter
            return this
        }

        fun withTemperature(temperature: Float): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.temperature = temperature
            return this
        }

        fun withModel(model: String): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.model = model
            return this
        }

        fun withFrequencyPenalty(frequencyPenalty: Float): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.frequencyPenalty = frequencyPenalty
            return this
        }

        fun withMaxTokens(maxTokens: Int): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.maxTokens = maxTokens
            return this
        }

        fun withTopP(topP: Float): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.topP = topP
            return this
        }

        fun withPresencePenalty(presencePenalty: Float): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions.PerplexityBuilder {
            perplexityChatOptions.presencePenalty = presencePenalty
            return this
        }

        fun build(): com.example.demo.infra.share.adapter.responser.infrastructure.PerplexityChatOptions = perplexityChatOptions
    }
}
