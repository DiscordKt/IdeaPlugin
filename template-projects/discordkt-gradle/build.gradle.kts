group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.8.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("me.jakejmattson", "DiscordKt", "0.23.4")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}