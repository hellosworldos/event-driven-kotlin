package com.spadar.eventdriven.domain

interface EventAware {
    fun popRecordedEvents(): List<Any>
}
