package me.jakejmattson.plugin.module

import com.intellij.openapi.module.ModuleType
import me.jakejmattson.plugin.utils.ICONS.DISCORDKT_16
import javax.swing.Icon

internal class DiscordKtModuleType : ModuleType<DiscordKtModuleBuilder>("DiscordKt") {
    override fun getName(): String {
        return "DiscordKt"
    }

    override fun getDescription(): String {
        return "DiscordKt Module"
    }

    val bigIcon: Icon
        get() = DISCORDKT_16

    override fun getNodeIcon( isOpened: Boolean): Icon {
        return DISCORDKT_16
    }

    override fun createModuleBuilder(): DiscordKtModuleBuilder {
        return DiscordKtModuleBuilder()
    }

    companion object {
        val instance = DiscordKtModuleType()
    }
}