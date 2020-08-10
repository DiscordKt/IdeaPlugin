package me.jakejmattson.discordkt.templates

import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.project.Project
import me.jakejmattson.discordkt.utils.IMPORTS

private const val PACKAGE_STATEMENT = "#if (${"$"}{PACKAGE_NAME} != \"\")package ${"$"}{PACKAGE_NAME}#end"
private const val FILE_NAME = "${"$"}{NAME}"

fun registerTemplates(project: Project) {
    val templateManager = FileTemplateManager.getInstance(project)

    templateManager.registerDiscordKtTemplate("CommandSet") {
        """
            $PACKAGE_STATEMENT

            ${IMPORTS.annotations}.CommandSet
            ${IMPORTS.dsl}.command.commands
            
            #if (${"$"}NAME.toString().endsWith("Commands"))
            @CommandSet("$FILE_NAME")
            fun $FILE_NAME() = commands {
            #else
            @CommandSet("$FILE_NAME")
            fun ${FILE_NAME}Commands() = commands {
            #end

            }
            
        """.trimIndent()
    }

    templateManager.registerDiscordKtTemplate("Service") {
        """
            $PACKAGE_STATEMENT
            
            ${IMPORTS.annotations}.Service

            #if (${"$"}NAME.toString().endsWith("Service"))
            @Service
            class $FILE_NAME() {

            }
            #else
            @Service
            class ${FILE_NAME}Service() {

            }
            #end
        """.trimIndent()
    }

    templateManager.registerDiscordKtTemplate("Data") {
        """
            $PACKAGE_STATEMENT
            
            ${IMPORTS.annotations}.Data

            @Data("path/to/data.json")
            data class $FILE_NAME()
        """.trimIndent()
    }

    templateManager.registerDiscordKtTemplate("Precondition") {
        """
            $PACKAGE_STATEMENT

            ${IMPORTS.annotations}.Precondition
            ${IMPORTS.dsl}.preconditions.Fail
            ${IMPORTS.dsl}.preconditions.Pass
            ${IMPORTS.dsl}.preconditions.precondition
            
            #if (${"$"}NAME.toString().endsWith("Precondition"))
            @Precondition
            fun produce$FILE_NAME() = precondition {
                return@precondition Pass
            }
            #else
            @Precondition
            fun produce${FILE_NAME}Precondition() = precondition {
                return@precondition Pass
            }
            #end
        """.trimIndent()
    }

    templateManager.registerDiscordKtTemplate("ArgumentType") {
        """
            $PACKAGE_STATEMENT

            ${IMPORTS.dsl}.arguments.ArgumentResult
            ${IMPORTS.dsl}.arguments.ArgumentType
            ${IMPORTS.dsl}.command.CommandEvent

            #if (${"$"}NAME.toString().endsWith("Arg"))
            open class $FILE_NAME(override val name: String = "$FILE_NAME") : ArgumentType<Any>() {
                companion object : $FILE_NAME()
            #else
            open class ${FILE_NAME}Arg(override val name: String = "$FILE_NAME") : ArgumentType<Any>() {
                companion object : ${FILE_NAME}Arg()
            #end

                override fun convert(arg: String, args: List<String>, event: CommandEvent<*>): ArgumentResult<Any> {
                    TODO("Not yet implemented")
                }

                override fun generateExamples(event: CommandEvent<*>): List<String> {
                    TODO("Not yet implemented")
                }
            }
        """.trimIndent()
    }

    templateManager.registerDiscordKtTemplate("Conversation") {
        """
            $PACKAGE_STATEMENT

            ${IMPORTS.dsl}.conversation.Conversation
            ${IMPORTS.dsl}.conversation.conversation

            #if (${"$"}NAME.toString().endsWith("Conversation"))
            class $FILE_NAME() : Conversation() {
            #else
            class ${FILE_NAME}Conversation() : Conversation() {
            #end
                @Start
                fun ${FILE_NAME}Conversation() = conversation {

                }
            }
        """.trimIndent()
    }
}

private fun FileTemplateManager.registerDiscordKtTemplate(name: String, template: () -> String) {
    addTemplate("DiscordKt $name", "kt").text = template.invoke()
}