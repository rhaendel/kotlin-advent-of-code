plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.kover)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.z3.turnkey)
    runtimeOnly(libs.logback.classic)

    testImplementation(libs.kotest.assertions.table)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.system.lambda)
    testRuntimeOnly(libs.logback.classic)
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
