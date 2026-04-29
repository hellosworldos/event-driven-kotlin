package com.spadar.eventdriven

class EventRecorder : EventAware {
    private val events: MutableList<Any> = mutableListOf()

    override fun recordEvents(vararg events: Any) {
        this.events.addAll(events)
    }

    override fun popRecordedEvents(): List<Any> {
        val recorded = events.toList()
        events.clear()
        return recorded
    }
}
