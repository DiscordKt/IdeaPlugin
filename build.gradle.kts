group = properties("pluginGroup")
version = properties("pluginVersion")

plugins {
    idea
    kotlin("jvm") version "1.5.20"
    id("org.jetbrains.intellij") version "1.1.2"
    id("com.github.ben-manes.versions") version "0.39.0"
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.22.0-SNAPSHOT")
}

intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    plugins.set(listOf("com.intellij.java"))
    type.set("IC")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    patchPluginXml {

    }

    register<Zip>("zipSimpleTemplate") {
        archiveFileName.set("Simple Template.zip")
        destinationDirectory.set(file("src/main/resources/projectTemplates"))

        from("template-projects/discordkt-gradle/")
        exclude(".idea/", ".gradle/")
    }
}

fun properties(key: String) = project.findProperty(key).toString()