package me.jakejmattson.kutils.framework

import java.io.File
import java.nio.file.Files

fun File.createCommonDirectories() {
    val commonFolders = listOf(
        File("$path/arguments"),
        File("$path/commands"),
        File("$path/preconditions"),
        File("$path/services")
    )

    commonFolders.forEach {
        Files.createDirectories(it.toPath())
    }
}

fun File.createMainApp(packageStatement: String) {
    val mainApp = File("$path/MainApp.kt")

    mainApp.createNewFile()

    mainApp.writeText(
        """
            package $packageStatement

            import me.aberrantfox.kjdautils.api.startBot
            
            fun main(args: Array<String>) {
                val token = args.firstOrNull() 
                    ?: throw IllegalArgumentException("No program arguments provided. Expected bot token.")
                
                startBot(token) { 
                    prefix = "+"
                }
            }
        """.trimIndent()
    )
}