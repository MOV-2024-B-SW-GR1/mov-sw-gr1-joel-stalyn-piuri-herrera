plugins {
    kotlin("jvm") version "2.0.20"
}

group = "ec.epn.edu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

}

tasks.test {
    useJUnitPlatform()
}