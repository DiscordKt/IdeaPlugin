package me.jakejmattson.kutils.templates

import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.project.Project

private const val PACKAGE_STATEMENT = "#if (${"$"}{PACKAGE_NAME} != \"\")package ${"$"}{PACKAGE_NAME}#end"
private const val FILE_NAME = "${"$"}{NAME}"

fun registerTemplates(project: Project) {
    val templateManager = FileTemplateManager.getInstance(project)

    templateManager.registerKUtilsTemplate("CommandSet") {
        """
            $PACKAGE_STATEMENT

            import me.jakejmattson.kutils.api.annotations.CommandSet
            import me.jakejmattson.kutils.api.dsl.command.commands
            
            #if (${"$"}NAME.toString().endsWith("Commands"))
            @CommandSet("$FILE_NAME")
            fun $FILE_NAME() = commands {
            #else
            @CommandSet("$FILE_NAME")
            fun ${FILE_NAME}Commands() = commands {
            #end
                command("CommandName") {
                    description = "Command description"
                    execute {
                        it.respond("Hello World")
                    }
                }
            }
            
        """.trimIndent()
    }

    templateManager.registerKUtilsTemplate("Service") {
        """
            $PACKAGE_STATEMENT
            
            import me.jakejmattson.kutils.api.annotations.Service

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

    templateManager.registerKUtilsTemplate("Data") {
        """
            $PACKAGE_STATEMENT
            
            import me.jakejmattson.kutils.api.annotations.Data

            @Data("path/to/data.json")
            data class $FILE_NAME()
        """.trimIndent()
    }

    templateManager.registerKUtilsTemplate("Precondition") {
        """
            $PACKAGE_STATEMENT

            import me.jakejmattson.kutils.api.annotations.Precondition
            import me.jakejmattson.kutils.api.dsl.preconditions.Fail
            import me.jakejmattson.kutils.api.dsl.preconditions.Pass
            import me.jakejmattson.kutils.api.dsl.preconditions.precondition
            
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

    templateManager.registerKUtilsTemplate("ArgumentType") {
        """
            $PACKAGE_STATEMENT
            
            import me.jakejmattson.kutils.api.dsl.arguments.ArgumentResult
            import me.jakejmattson.kutils.api.dsl.arguments.ArgumentType
            import me.jakejmattson.kutils.api.dsl.command.CommandEvent
            
            #if (${"$"}NAME.toString().endsWith("Arg"))
            open class ${FILE_NAME}(override val name: String = "") : ArgumentType<Any>() {
                companion object : $FILE_NAME()
            #else
            open class ${FILE_NAME}Arg(override val name: String = "") : ArgumentType<Any>() {
                companion object : ${FILE_NAME}Arg()
            #end

                override fun convert(arg: String, args: List<String>, event: CommandEvent<*>): ArgumentResult<Any> {
                    return ArgumentResult.Success("")
                }

                override fun generateExamples(event: CommandEvent<*>): List<String> {
                    TODO("Not yet implemented")
                }
            }
        """.trimIndent()
    }

    templateManager.registerKUtilsTemplate("Conversation") {
        """
            $PACKAGE_STATEMENT
            
            import me.jakejmattson.kutils.api.dsl.conversation.Conversation
            import me.jakejmattson.kutils.api.dsl.conversation.conversation
            
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

private fun FileTemplateManager.registerKUtilsTemplate(name: String, template: () -> String) {
    addTemplate("KUtils $name", "kt").text = template.invoke()
}