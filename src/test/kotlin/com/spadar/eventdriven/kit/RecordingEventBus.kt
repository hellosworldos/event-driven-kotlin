package com.spadar.eventdriven.kit

import com.spadar.eventdriven.application.EventBus

class RecordingEventBus : EventBus {
    val dispatched = mutableListOf<Any>()

    override fun dispatch(vararg events: Any) {
        dispatched.addAll(events)
    }
}
