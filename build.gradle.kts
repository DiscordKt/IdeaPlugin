group = "me.jakejmattson"
version = "0.5.0-SNAPSHOT"

plugins {
    idea
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.13.3"
    id("com.github.ben-manes.versions") version "0.46.0"
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.23.4")
}

intellij {
    version.set("2023.1")
    type.set("IU")
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

    register<Zip>("zipSimpleTemplate") {
        archiveFileName.set("Simple Template.zip")
        destinationDirectory.set(file("src/main/resources/projectTemplates"))

        from("template-projects/discordkt-gradle/")
        exclude(".idea/", ".gradle/")
    }
}
