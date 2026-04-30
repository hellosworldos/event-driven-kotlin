# event-driven-kotlin

A Kotlin library that ports the PHP
[friends-of-ddd/event-driven](https://packagist.org/packages/friends-of-ddd/event-driven) library.
It provides lightweight, framework-agnostic abstractions for the **Command Bus** and **Event Bus**
patterns used in Domain-Driven Design, plus a delegate helper for recording domain events inside
entities.

An optional adapter for the [Axon Framework](https://www.axoniq.io/) is bundled in the same
module; it only requires Axon on the classpath when the adapter classes are actually used (see
[Axon adapters](#axon-adapters) below).

---

## Requirements

- **JVM**: 21+
- **Kotlin**: 2.1+

## Installation

Add the library to your Gradle build:

```kotlin
dependencies {
    implementation("com.spadar:event-driven-kotlin:1.0.0")

    // Only needed if you use AxonCommandBusAdapter / AxonEventBusAdapter
    implementation("org.axonframework:axon-messaging:4.10.3")
}
```

---

## Core concepts

### CommandBus

```kotlin
interface CommandBus {
    fun dispatch(vararg commands: Any)
}
```

Implement this interface to create a command bus for your application. No marker interface is
required on command types.

```kotlin
class MyCommandBus(private val delegate: SomeFrameworkBus) : CommandBus {
    override fun dispatch(vararg commands: Any) {
        commands.forEach { delegate.send(it) }
    }
}

// Single or multiple commands at once
commandBus.dispatch(CreateTicketCommand("Fix login issue", clientId = 123))
commandBus.dispatch(
    CreateTicketCommand("First ticket", 123),
    CreateTicketCommand("Second ticket", 124),
)
```

### EventBus

```kotlin
interface EventBus {
    fun dispatch(vararg events: Any)
}
```

Same pattern as `CommandBus`, but for domain events.

```kotlin
eventBus.dispatch(TicketCreatedEvent(ticketId = 1, title = "Fix login issue"))
```


### EventAware + EventRecorder

Domain entities can record events without inheriting from a base class by using composition with `EventRecorder`:

```kotlin
class Ticket(
    val id: Int,
    private val recorder: EventRecorder = EventRecorder(),
) : EventAware {
    fun recordEvents(vararg events: Any) = recorder.recordEvents(*events)
    override fun popRecordedEvents(): List<Any> = recorder.popRecordedEvents()

    companion object {
        fun create(id: Int, title: String, clientId: Int): Ticket {
            val ticket = Ticket(id)
            ticket.recordEvents(TicketCreatedEvent(id, title))
            return ticket
        }
    }
}

// In an application service / command handler:
val ticket = Ticket.create(id = 1, title = "Fix login issue", clientId = 123)

val events = ticket.popRecordedEvents() // clears internal list, returns snapshot
eventBus.dispatch(*events.toTypedArray())
```

Here, `EventAware` only exposes `popRecordedEvents()`. The `recordEvents()` method is not part of the interface and can be kept private to the entity. `EventRecorder` holds the event state and provides the actual recording logic.

`popRecordedEvents()` returns a snapshot of recorded events **and clears** the internal list, so each event is dispatched exactly once.

---

## Axon adapters

The `adapter/axon` package contains ready-made implementations that bridge this library's interfaces
to Axon Framework's command and event buses.

> **Optional dependency** — `axon-messaging` is declared `compileOnly` in this library's own build,
> which means it is **not** transitively added to your project's classpath. You must add the Axon
> dependency yourself (see [Installation](#installation)) only if you use these adapters.

### AxonCommandBusAdapter

```kotlin
import com.spadar.eventdriven.adapter.axon.AxonCommandBusAdapter
import org.axonframework.commandhandling.CommandBus

val commandBus: CommandBus = SimpleCommandBus.builder().build()
val bus = AxonCommandBusAdapter(commandBus)

bus.dispatch(CreateTicketCommand("Fix login issue", clientId = 123))
```

### AxonEventBusAdapter

```kotlin
import com.spadar.eventdriven.adapter.axon.AxonEventBusAdapter
import org.axonframework.eventhandling.EventBus

val eventBus: EventBus = SimpleEventBus.builder().build()
val bus = AxonEventBusAdapter(eventBus)

bus.dispatch(TicketCreatedEvent(ticketId = 1, title = "Fix login issue"))
```

---

## Local development

A `Makefile` and `Dockerfile` are provided so you can run all checks without installing a local JDK.

### With a local JDK (JVM 21+)

```bash
make test        # unit tests
make lint        # ktlint
make lint-fix    # auto-fix ktlint violations
make analyze     # detekt static analysis
make check       # lint + analyze + test
make build       # full Gradle build
```

### Inside Docker (`gradle:8.13-jdk21-alpine`)

```bash
make docker-check   # docker-lint + docker-analyze + docker-test
```

---

## CI

GitHub Actions runs three parallel jobs on every push and pull request:

| Job | Command |
|-----|---------|
| Lint | `./gradlew ktlintCheck` |
| Static Analysis | `./gradlew detekt` |
| Unit Tests | `./gradlew test` |

Test results are uploaded as a build artifact when tests run.

---

## License

MIT — see [LICENSE](LICENSE).