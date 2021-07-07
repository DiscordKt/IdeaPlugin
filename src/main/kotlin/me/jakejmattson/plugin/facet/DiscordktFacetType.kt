package me.jakejmattson.plugin.facet

import com.intellij.facet.Facet
import com.intellij.facet.FacetType
import com.intellij.facet.FacetTypeId
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import me.jakejmattson.plugin.utils.ICONS
import javax.swing.Icon

class DiscordKtFacetType : FacetType<DiscordKtFacet, DiscordKtFacetConfiguration>(ID, "DiscordKt", "DiscordKt") {

    override fun createFacet(module: Module, name: String, configuration: DiscordKtFacetConfiguration, underlyingFacet: Facet<*>?) =
            DiscordKtFacet(this, module, name, configuration, underlyingFacet)

    override fun createDefaultConfiguration() = DiscordKtFacetConfiguration()
    override fun isSuitableModuleType(moduleType: ModuleType<*>?) = true
    override fun getIcon(): Icon = ICONS.DISCORDKT_16

    companion object {
        val ID = FacetTypeId<DiscordKtFacet>("DiscordKt")
        val INSTANCE = DiscordKtFacetType()
    }
}