package org.bitcoin.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.bitcoin.consumer.dto.BitumbOrderbookResponseDTO
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener

@Configuration
class BithumbConsumer(
    val objectMapper: ObjectMapper,
    val bithumbService: BithumbService
) {

    @KafkaListener(topics = ["bithumb"], groupId = "bitcoin")
    fun getBitumbOrderBookData(message: String) {
        val deserializeData =
            objectMapper.deserialize(message, BitumbOrderbookResponseDTO::class.java)

        bithumbService.saveOrderBookData(deserializeData)
    }

    fun <T> ObjectMapper.deserialize(data: String, clazz: Class<T>): T = readValue(data, clazz)

}