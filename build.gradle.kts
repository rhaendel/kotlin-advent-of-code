plugins {
    kotlin("jvm") version "2.2.10"
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    // for tapSystemOut
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")
    testImplementation("io.kotest:kotest-runner-junit5:6.0.0")
    testImplementation("io.kotest:kotest-assertions-table:6.0.0")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.5.18")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    named<Test>("test") {
        useJUnitPlatform()
    }
}
