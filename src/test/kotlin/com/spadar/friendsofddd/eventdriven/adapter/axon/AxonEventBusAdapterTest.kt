package com.spadar.friendsofddd.eventdriven.adapter.axon

import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.axonframework.eventhandling.EventMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import org.axonframework.eventhandling.EventBus as AxonEventBus

class AxonEventBusAdapterTest {
    private val axonEventBus: AxonEventBus = mockk(relaxed = true)
    private val adapter = AxonEventBusAdapter(axonEventBus)

    @Test
    fun `should dispatch single event to axon event bus`() {
        val event = "TestEvent"
        val slot = slot<List<EventMessage<*>>>()

        adapter.dispatch(event)

        verify { axonEventBus.publish(capture(slot)) }
        assertEquals(1, slot.captured.size)
        assertEquals(event, slot.captured[0].payload)
    }

    @Test
    fun `should dispatch multiple events to axon event bus`() {
        val event1 = "Event1"
        val event2 = "Event2"
        val slot = slot<List<EventMessage<*>>>()

        adapter.dispatch(event1, event2)

        verify { axonEventBus.publish(capture(slot)) }
        assertEquals(2, slot.captured.size)
        assertEquals(event1, slot.captured[0].payload)
        assertEquals(event2, slot.captured[1].payload)
    }
}
