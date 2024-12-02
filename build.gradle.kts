plugins {
    kotlin("jvm") version "1.9.0"  // Use the latest version of Kotlin
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.register<JavaExec>("run") {
    group = "application"
    mainClass.set("HelloWorld")  // Refers to the name of the Kotlin file without the `.kt` extension
    classpath = sourceSets["main"].runtimeClasspath
}
