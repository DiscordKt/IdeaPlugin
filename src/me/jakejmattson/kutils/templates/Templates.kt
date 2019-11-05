package me.jakejmattson.kutils.templates

import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.project.Project

private const val DOLLAR_SIGN = "$"
private const val PACKAGE_STATEMENT = "#if ($DOLLAR_SIGN{PACKAGE_NAME} != \"\")package $DOLLAR_SIGN{PACKAGE_NAME}#end"
private const val FILE_NAME = "$DOLLAR_SIGN{NAME}"

fun registerTemplates(project: Project) {
    val templateManager = FileTemplateManager.getInstance(project)

    templateManager.registerKUtilsTemplate("CommandSet") {
        """
            $PACKAGE_STATEMENT

            import me.aberrantfox.kjdautils.api.dsl.command.CommandSet
            
            @CommandSet("$FILE_NAME")
            fun ${FILE_NAME}Commands() {
            
            }
        """.trimIndent()
    }

    templateManager.registerKUtilsTemplate("Service") {
        """
            $PACKAGE_STATEMENT
            
            import me.aberrantfox.kjdautils.api.annotation.Service
            
            @Service
            class $FILE_NAME() {

            }
        """.trimIndent()
    }

    templateManager.registerKUtilsTemplate("Data") {
        """
            $PACKAGE_STATEMENT
            
            import me.aberrantfox.kjdautils.api.annotation.Data

            @Data("path/to/data.json")
            data class $FILE_NAME()
        """.trimIndent()
    }

    templateManager.registerKUtilsTemplate("Precondition") {
        """
            $PACKAGE_STATEMENT

            import me.aberrantfox.kjdautils.api.dsl.Precondition
            import me.aberrantfox.kjdautils.api.dsl.precondition
            import me.aberrantfox.kjdautils.internal.command.Fail
            import me.aberrantfox.kjdautils.internal.command.Pass
            
            @Precondition
            fun produce${FILE_NAME}Precondition() = precondition {
                return@precondition Pass
            }
        """.trimIndent()
    }

    templateManager.registerKUtilsTemplate("ArgumentType") {
        """
            $PACKAGE_STATEMENT
            
            import me.aberrantfox.kjdautils.api.dsl.command.CommandEvent
            import me.aberrantfox.kjdautils.internal.command.ArgumentType
            import me.aberrantfox.kjdautils.internal.command.ArgumentResult
            import me.aberrantfox.kjdautils.internal.command.ConsumptionType
            
            open class ${FILE_NAME}(override val name: String = "") : ArgumentType<Any>() {
                companion object : $FILE_NAME()
            
                override val examples = arrayListOf("")
                override val consumptionType = ConsumptionType.Single
                override fun convert(arg: String, args: List<String>, event: CommandEvent<*>): ArgumentResult<Any> {
                    return ArgumentResult.Success("")
                }
            }
        """.trimIndent()
    }
}

private fun FileTemplateManager.registerKUtilsTemplate(name: String, template: () -> String) {
    addTemplate("KUtils $name", "kt").text = template.invoke()
}