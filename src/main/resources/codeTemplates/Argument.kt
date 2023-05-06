package codeTemplates

import me.jakejmattson.discordkt.Discord
import me.jakejmattson.discordkt.arguments.Result
import me.jakejmattson.discordkt.arguments.StringArgument
import me.jakejmattson.discordkt.commands.DiscordContext

open class TemplateArg(
    override val name: String = "Template",
    override val description: String = ""
) : StringArgument<Any> {
    companion object : TemplateArg()

    override suspend fun parse(args: MutableList<String>, discord: Discord): String? {
        TODO("Not yet implemented")
    }

    override suspend fun transform(input: String, context: DiscordContext): Result<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun generateExamples(context: DiscordContext): List<String> {
        TODO("Not yet implemented")
    }
}