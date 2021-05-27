group = "me.jakejmattson"
version = "0.3.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.intellij") version "0.7.3"
    id("com.github.ben-manes.versions") version "0.39.0"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.21.3")
}

intellij {
    pluginName = "DiscordKt"
    version = "2020.1.2"
    setPlugins("java")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    patchPluginXml {
        changeNotes(
            """
                The code is different now!
            """.trimIndent()
        )
    }
}