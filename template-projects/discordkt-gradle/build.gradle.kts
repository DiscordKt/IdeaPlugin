group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.5.20"
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("me.jakejmattson", "DiscordKt", "0.22.0-SNAPSHOT")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}