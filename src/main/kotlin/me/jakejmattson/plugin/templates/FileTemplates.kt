package me.jakejmattson.plugin.templates

import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.project.Project

private const val PACKAGE_STATEMENT = "#if (${"$"}{PACKAGE_NAME} != \"\")package ${"$"}{PACKAGE_NAME}#end"
private const val FILE_NAME = "${"$"}{NAME}"

fun registerTemplates(project: Project) {
    val templateManager = FileTemplateManager.getInstance(project)

    templateManager.registerDiscordKtTemplate("CommandSet") {
        it.replace("commandsName", FILE_NAME)
    }

    templateManager.registerDiscordKtTemplate("Service") {
        it.replace("TemplateService", FILE_NAME)
    }

    templateManager.registerDiscordKtTemplate("Data") {
        it.replace("DataClass(val x: Int)", "$FILE_NAME()")
    }

    templateManager.registerDiscordKtTemplate("Precondition") {
        it.replace("preconditionName", FILE_NAME)
    }

    templateManager.registerDiscordKtTemplate("ArgumentType") {
        it.replace("Template", FILE_NAME)
    }

    templateManager.registerDiscordKtTemplate("Conversation") {
        it.replace("conversationName", FILE_NAME)
    }
}

private class Reader

private fun readCodeContent(fileName: String) = """
    $PACKAGE_STATEMENT
    ${Reader::class.java.getResource("/codeTemplates/${fileName}.kt").readText().split("\n").drop(1).joinToString("\n")}
""".trimIndent()

private fun FileTemplateManager.registerDiscordKtTemplate(name: String, template: (String) -> String) {
    addTemplate("DiscordKt $name", "kt").text = template.invoke(readCodeContent(name))
}