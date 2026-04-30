package com.spadar.eventdriven.infrastructure.adapter.axon

import com.spadar.eventdriven.application.CommandBus
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.commandhandling.CommandBus as AxonCommandBus

class AxonCommandBusAdapter(
    private val axonCommandBus: AxonCommandBus,
) : CommandBus {
    override fun dispatch(vararg commands: Any) {
        commands.forEach { command ->
            axonCommandBus.dispatch(GenericCommandMessage.asCommandMessage<Any>(command))
        }
    }
}
