package com.spadar.eventdriven.infrastructure.inmemory

import com.spadar.eventdriven.kit.RecordingEventBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InMemoryEventStreamProviderTest {
    private val delegate = RecordingEventBus()
    private val provider = InMemoryEventStreamProvider(delegate)

    data class SomeEvent(
        val id: String,
    )

    @Test
    fun `dispatch stores events and forwards to delegate`() {
        val event = SomeEvent("1")
        provider.dispatch(event)

        assertEquals(1, delegate.dispatched.size)
        assertEquals(event, delegate.dispatched[0])
    }

    @Test
    fun `getNewEvents returns all stored events`() {
        val e1 = SomeEvent("1")
        val e2 = SomeEvent("2")
        provider.dispatch(e1, e2)

        val events = provider.getNewEvents()
        assertEquals(2, events.size)
        assertEquals(e1, events[0])
        assertEquals(e2, events[1])
    }

    @Test
    fun `getNewEvents clears stored events`() {
        provider.dispatch(SomeEvent("1"))
        provider.getNewEvents()

        val second = provider.getNewEvents()
        assertTrue(second.isEmpty())
    }

    @Test
    fun `multiple dispatches accumulate events`() {
        provider.dispatch(SomeEvent("1"))
        provider.dispatch(SomeEvent("2"))

        val events = provider.getNewEvents()
        assertEquals(2, events.size)
    }

    @Test
    fun `getNewEvents after clear returns only new events`() {
        provider.dispatch(SomeEvent("1"))
        provider.getNewEvents()

        provider.dispatch(SomeEvent("2"))
        val events = provider.getNewEvents()

        assertEquals(1, events.size)
        assertEquals(SomeEvent("2"), events[0])
    }
}
