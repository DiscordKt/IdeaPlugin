package org.example.bot

import dev.kord.common.annotation.KordPreview
import dev.kord.common.kColor
import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.extensions.addField
import me.jakejmattson.discordkt.api.extensions.profileLink
import java.awt.Color

@KordPreview
suspend fun main(args: Array<String>) {
    val token = args.firstOrNull()
    require(token != null) { "Expected the bot token as a command line argument!" }

    bot(token) {
        prefix {
            "+"
        }

        configure {
            theme = Color(0x00BFFF)
        }

        mentionEmbed {
            color = it.discord.configuration.theme?.kColor

            author {
                with(it.author) {
                    icon = avatar.url
                    name = tag
                    url = profileLink
                }
            }

            thumbnail {
                url = it.discord.kord.getSelf().avatar.url
            }

            footer {
                val versions = it.discord.versions
                text = "${versions.library} - ${versions.kord} - ${versions.kotlin}"
            }

            addField("Prefix", it.prefix())
        }
    }
}