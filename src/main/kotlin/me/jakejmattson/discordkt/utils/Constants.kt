package me.jakejmattson.discordkt.utils

import javax.swing.ImageIcon

object IMPORTS {
    private const val apiBase = "import me.jakejmattson.discordkt.api"

    const val dsl = "$apiBase.dsl"
    const val annotations = "$apiBase.annotations"
}

object ICONS {
    val DISCORDKT_16 = ImageIcon(javaClass.getResource("/icons/DiscordKt_16.png"))
}