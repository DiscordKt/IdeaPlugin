package me.jakejmattson.plugin.module

import com.intellij.openapi.module.*
import me.jakejmattson.plugin.utils.ICONS

private const val ID = "DISCORDKT_MODULE"

class DiscordKtModuleType : ModuleType<DiscordKtModuleBuilder>(ID) {
    override fun getName() = "DiscordKt"
    override fun getDescription() = "DiscordKt module type"
    override fun getNodeIcon(b: Boolean) = ICONS.DISCORDKT_16
    override fun createModuleBuilder() = DiscordKtModuleBuilder()

    companion object {
        fun getInstance() = ModuleTypeManager.getInstance().findByID(ID) as DiscordKtModuleType
    }
}