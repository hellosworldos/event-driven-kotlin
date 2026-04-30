package com.spadar.eventdriven.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EventRecorderTest {
    private val recorder = EventRecorder()

    @Test
    fun `should record events`() {
        val event1 = "event1"
        val event2 = "event2"

        recorder.recordEvents(event1, event2)

        val recorded = recorder.popRecordedEvents()
        assertEquals(listOf(event1, event2), recorded)
    }

    @Test
    fun `should clear events after popping`() {
        recorder.recordEvents("event")
        recorder.popRecordedEvents()

        assertTrue(recorder.popRecordedEvents().isEmpty())
    }

    @Test
    fun `should return empty list when no events recorded`() {
        assertTrue(recorder.popRecordedEvents().isEmpty())
    }

    @Test
    fun `should support delegation in domain entities`() {
        class TestEntity(
            private val eventRecorder: EventRecorder = EventRecorder(),
        ) : EventAware by eventRecorder {
            fun doSomething() {
                eventRecorder.recordEvents("domain_event_happened")
            }
        }

        val entity = TestEntity()
        entity.doSomething()

        val events = entity.popRecordedEvents()
        assertEquals(listOf("domain_event_happened"), events)
    }
}
