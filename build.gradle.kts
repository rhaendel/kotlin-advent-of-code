plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.testballoon)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.z3.turnkey)
    runtimeOnly(libs.logback.classic)

    testImplementation(libs.system.lambda)
    testImplementation(libs.testballoon)
    testImplementation(libs.testballoon.kotest.assertions)
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
