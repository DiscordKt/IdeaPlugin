package me.jakejmattson.plugin.framework

import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.ide.util.frameworkSupport.FrameworkSupportProvider
import com.intellij.openapi.module.ModuleType
import me.jakejmattson.plugin.facet.DiscordKtFacetType
import me.jakejmattson.plugin.module.DiscordKtModuleType
import me.jakejmattson.plugin.utils.ICONS
import javax.swing.Icon

class DiscordKtFrameworkSupportProvider : FrameworkSupportProvider("DiscordKt", DiscordKtFacetType.INSTANCE.presentableName) {
    override fun getIcon(): Icon = ICONS.DISCORDKT_16
    override fun createConfigurable(model: FrameworkSupportModel) = DiscordKtFrameworkSupportConfigurable(model)
    override fun isEnabledForModuleType(moduleType: ModuleType<*>) = moduleType is DiscordKtModuleType
}