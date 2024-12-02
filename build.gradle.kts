plugins {
    kotlin("jvm") version "1.9.0"  // Use the latest version of Kotlin
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.register<JavaExec>("day1") {
    group = "application"
    mainClass.set("Day1")  // Refers to the name of the Kotlin file without the `.kt` extension
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("day2") {
    group = "application"
    mainClass.set("Day2")  // Refers to the name of the Kotlin file without the `.kt` extension
    classpath = sourceSets["main"].runtimeClasspath
}
