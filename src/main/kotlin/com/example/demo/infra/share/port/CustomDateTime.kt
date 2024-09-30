package com.example.demo.infra.share.port

import java.time.LocalDateTime

interface CustomDateTime {
    fun getNow(): LocalDateTime
}
