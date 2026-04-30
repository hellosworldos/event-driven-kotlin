package com.spadar.eventdriven.infrastructure.adapter.axon

import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.axonframework.commandhandling.CommandMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import org.axonframework.commandhandling.CommandBus as AxonCommandBus

class AxonCommandBusAdapterTest {
    private val axonCommandBus: AxonCommandBus = mockk(relaxed = true)
    private val adapter = AxonCommandBusAdapter(axonCommandBus)

    @Test
    fun `should dispatch single command to axon command bus`() {
        val command = "TestCommand"
        val slot = slot<CommandMessage<*>>()

        adapter.dispatch(command)

        verify { axonCommandBus.dispatch(capture(slot)) }
        assertEquals(command, slot.captured.payload)
    }

    @Test
    fun `should dispatch multiple commands to axon command bus`() {
        val command1 = "Command1"
        val command2 = "Command2"

        adapter.dispatch(command1, command2)

        verify(exactly = 2) { axonCommandBus.dispatch(any<CommandMessage<*>>()) }
    }
}
