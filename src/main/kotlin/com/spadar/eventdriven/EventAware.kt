package com.spadar.eventdriven

interface EventAware {
    fun recordEvents(vararg events: Any)

    fun popRecordedEvents(): List<Any>
}
