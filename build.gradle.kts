group = properties("pluginGroup")
version = properties("pluginVersion")

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
    implementation("me.jakejmattson:DiscordKt:0.22.0")
}

intellij {
    version.set("2023.1")
    type.set("IU")
    plugins.set(listOf("org.jetbrains.kotlin", "com.intellij.java"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")
    }

    register<Zip>("zipSimpleTemplate") {
        archiveFileName.set("Simple Template.zip")
        destinationDirectory.set(file("src/main/resources/projectTemplates"))

        from("template-projects/discordkt-gradle/")
        exclude(".idea/", ".gradle/")
    }
}

fun properties(key: String) = project.findProperty(key).toString()