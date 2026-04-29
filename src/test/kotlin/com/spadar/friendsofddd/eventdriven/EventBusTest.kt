package com.spadar.friendsofddd.eventdriven

import kotlin.test.Test
import kotlin.test.assertEquals

class EventBusTest {
    @Test
    fun `should dispatch events`() {
        val dispatched = mutableListOf<Any>()
        val eventBus =
            object : EventBus {
                override fun dispatch(vararg events: Any) {
                    dispatched.addAll(events.toList())
                }
            }

        eventBus.dispatch("event1", "event2")

        assertEquals(listOf<Any>("event1", "event2"), dispatched)
    }
}
