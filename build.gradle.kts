plugins {
    kotlin("jvm") version "2.2.0"
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    // for tapSystemOut
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
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
