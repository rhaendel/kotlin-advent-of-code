plugins {
    kotlin("jvm") version "2.1.0"
    java
//    id("org.barfuin.gradle.jacocolog") version "3.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.4")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("org.assertj:assertj-core:3.27.2")
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
