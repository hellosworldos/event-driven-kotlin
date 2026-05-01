package com.spadar.eventdriven.infrastructure.inmemory

import com.spadar.eventdriven.application.EventBus

class InMemoryEventStreamProvider(
    private val delegate: EventBus,
) : EventBus {
    private val storedEvents = mutableListOf<Any>()

    override fun dispatch(vararg events: Any) {
        storedEvents.addAll(events)
        delegate.dispatch(*events)
    }

    fun getNewEvents(): List<Any> {
        val result = storedEvents.toList()
        storedEvents.clear()
        return result
    }
}
