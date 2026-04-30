package com.spadar.eventdriven.infrastructure.adapter.axon

import com.spadar.eventdriven.application.EventBus
import org.axonframework.eventhandling.GenericEventMessage
import org.axonframework.eventhandling.EventBus as AxonEventBus

class AxonEventBusAdapter(
    private val axonEventBus: AxonEventBus,
) : EventBus {
    override fun dispatch(vararg events: Any) {
        val eventMessages = events.map { GenericEventMessage.asEventMessage<Any>(it) }
        axonEventBus.publish(eventMessages)
    }
}
