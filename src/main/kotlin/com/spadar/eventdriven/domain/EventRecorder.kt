package com.spadar.eventdriven.domain

class EventRecorder : EventAware {
    private val events: MutableList<Any> = mutableListOf()

    fun recordEvents(vararg events: Any) {
        this.events.addAll(events)
    }

    override fun popRecordedEvents(): List<Any> {
        val recorded = events.toList()
        events.clear()
        return recorded
    }
}
