# AGENTS.md

## Project overview

`event-driven-kotlin` is a Kotlin library that ports the PHP
[friends-of-ddd/event-driven](https://packagist.org/packages/friends-of-ddd/event-driven) library.
It provides lightweight abstractions for the **Command Bus** and **Event Bus** patterns used in
Domain-Driven Design, along with a delegate helper for recording domain events inside entities.

An optional adapter for the [Axon Framework](https://www.axoniq.io/) is included in the same module;
it requires Axon on the classpath only when the adapter classes are actually used.

### Package structure

```
com.spadar.friendsofddd.eventdriven
├── CommandBus.kt          # CommandBus interface
├── EventBus.kt            # EventBus interface
├── EventAware.kt          # EventAware interface (record / pop events)
├── EventRecorder.kt       # Delegate implementation of EventAware
└── adapter/
    └── axon/
        ├── AxonCommandBusAdapter.kt  # Wraps Axon CommandBus
        └── AxonEventBusAdapter.kt    # Wraps Axon EventBus
```

## Build & tooling

| Tool | Version | Purpose |
|------|---------|---------|
| Kotlin | 2.1.20 | Language |
| Gradle | 8.13 | Build tool |
| JVM | 21 | Target JVM |
| ktlint | 1.5.0 | Code style linter |
| detekt | 1.23.8 | Static analysis |
| Mockk | 1.13.17 | Mocking in tests |
| Axon Framework | 4.10.3 | Optional adapter dependency (`compileOnly`) |

### Common commands

```bash
make test          # run unit tests
make lint          # run ktlint
make lint-fix      # auto-fix ktlint violations
make analyze       # run detekt
make check         # lint + analyze + test
make build         # full Gradle build
```

All targets also have `docker-*` variants that run inside the official
`gradle:8.13-jdk21-alpine` image without requiring a local JDK:

```bash
make docker-check  # docker-lint + docker-analyze + docker-test
```

## CI

GitHub Actions runs three parallel jobs on every push and pull request:

- **Lint** — `./gradlew ktlintCheck`
- **Static Analysis** — `./gradlew detekt`
- **Unit Tests** — `./gradlew test` (test-results artifact uploaded on failure)

## Conventions

- All public interfaces accept `vararg …: Any` — no marker interfaces are required on command or
  event types.
- Domain entities should use Kotlin's `by` delegation with `EventRecorder` rather than inheriting
  from a base class.
- The Axon adapter dependency (`axon-messaging`) is declared `compileOnly`; consumers who use the
  adapters must add Axon to their own dependency configuration.
- Code style is enforced by ktlint; static analysis by detekt with the config at
  `config/detekt/detekt.yml`.
- Tests are written with `kotlin.test` and Mockk.
