package com.spadar.friendsofddd.eventdriven

interface EventAware {
    fun recordEvents(vararg events: Any)

    fun popRecordedEvents(): List<Any>
}
