plugins {
    kotlin("jvm") version "2.1.0"
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.assertj:assertj-core:3.27.1")
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
    named<Test>("test") {
        useJUnitPlatform()
    }
}
