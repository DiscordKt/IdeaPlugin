package codeTemplates

import me.jakejmattson.discordkt.api.arguments.ArgumentResult
import me.jakejmattson.discordkt.api.arguments.ArgumentType
import me.jakejmattson.discordkt.api.dsl.CommandEvent

open class TemplateArg(override val name: String = "Template") : ArgumentType<Any> {
    companion object : TemplateArg()

    override val description: String
        get() = TODO("Not yet implemented")

    override suspend fun convert(arg: String, args: List<String>, event: CommandEvent<*>): ArgumentResult<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun generateExamples(event: CommandEvent<*>): List<String> {
        TODO("Not yet implemented")
    }
}