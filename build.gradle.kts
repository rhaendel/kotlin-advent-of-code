plugins {
    kotlin("jvm") version "2.1.21"
    java
//    id("org.barfuin.gradle.jacocolog") version "3.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    testImplementation("io.kotest:kotest-runner-junit5:6.0.0.M4")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("ch.qos.logback:logback-classic:1.5.18")

    // for tapSystemOut
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
    named<Test>("test") {
        useJUnitPlatform()
//        finalizedBy(jacocoLogTestCoverage)
    }
}
