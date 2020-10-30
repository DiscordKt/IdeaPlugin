package me.jakejmattson.plugin.templates.raw

import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.CommandEvent

open class TemplateArg(override val name: String = "Template") : ArgumentType<Any>() {
    companion object : TemplateArg()

    override suspend fun convert(arg: String, args: List<String>, event: CommandEvent<*>): ArgumentResult<Any> {
        TODO("Not yet implemented")
    }

    override fun generateExamples(event: CommandEvent<*>): List<String> {
        TODO("Not yet implemented")
    }
}