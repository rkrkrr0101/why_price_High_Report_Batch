package com.example.demo.infra.config

import io.github.cdimascio.dotenv.Dotenv

class ApiConfig private constructor() {
    companion object {
        private val dotenv = Dotenv.configure().ignoreIfMissing().load()

        fun getOpenApiKey(): String = dotenv.get("OPENAPI_KEY")

        fun getPerplexityKey(): String = dotenv.get("PERPLEXITY_KEY")

        fun getKoreaInvKey(): String = dotenv.get("KOREA_INV_KEY")

        fun getKoreaSecretKey(): String = dotenv.get("KOREA_INV_SECRET")

        fun getOpenAiKey(): String = dotenv.get("OPENAI_GPT_KEY")
    }
}
