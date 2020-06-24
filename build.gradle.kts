group = "me.jakejmattson"
version = "0.2.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.intellij") version "0.4.21"
}

repositories {
    mavenCentral()
}

dependencies {

}

intellij {
    pluginName = "KUtils"
    version = "2020.1.2"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    patchPluginXml {
        this.changeNotes(
                """
            The code is different now!
        """.trimIndent()
        )
    }
}