package com.example.demo.infra.share.impl

import com.example.demo.infra.share.port.CustomDateTime
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomDateTimeImpl : CustomDateTime {
    override fun getNow(): LocalDateTime = LocalDateTime.now()
}
