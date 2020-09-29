group = "me.jakejmattson"
version = "0.2.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.4.10"
    id("org.jetbrains.intellij") version "0.4.21"
}

repositories {
    mavenCentral()
}

dependencies {

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