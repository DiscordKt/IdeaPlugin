group = "me.jakejmattson"
version = "0.6.0"

plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.13.3"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.23.4")
}

intellij {
    type.set("IU")
    version.set("2023.1")
    plugins.set(listOf("org.jetbrains.kotlin", "com.intellij.java"))
}

tasks {
    compileJava {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("231")
    }

    buildPlugin {
        archiveBaseName.set("discordkt-plugin")
    }

    register<Zip>("zipSimpleTemplate") {
        archiveFileName.set("Simple Template.zip")
        destinationDirectory.set(file("src/main/resources/projectTemplates"))

        from("template-projects/discordkt-gradle/")
        exclude(".idea/", ".gradle/")
    }
}
