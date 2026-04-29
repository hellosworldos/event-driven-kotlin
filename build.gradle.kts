import io.gitlab.arturbosch.detekt.Detekt

plugins {
    kotlin("jvm") version "2.1.20"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
}

group = "com.spadar"
version = "1.0.0"

repositories {
    mavenCentral()
}

val axonVersion = "4.10.3"

dependencies {
    compileOnly("org.axonframework:axon-messaging:$axonVersion")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.17")
    testImplementation("org.axonframework:axon-messaging:$axonVersion")
    testImplementation("org.axonframework:axon-test:$axonVersion")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$projectDir/config/detekt/detekt.yml"))
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
    }
}

ktlint {
    version.set("1.5.0")
}
