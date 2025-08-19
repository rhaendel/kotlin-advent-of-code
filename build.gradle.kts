plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.kover)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    // for tapSystemOut
    testImplementation(libs.system.lambda)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.table)
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
