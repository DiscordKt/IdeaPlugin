package me.jakejmattson.kutils.framework

import java.io.File

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