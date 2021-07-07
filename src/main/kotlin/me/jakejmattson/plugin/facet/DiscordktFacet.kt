package me.jakejmattson.plugin.facet

import com.intellij.application.options.CodeStyle
import com.intellij.facet.Facet
import com.intellij.facet.FacetManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.psi.codeStyle.PackageEntry
import org.jetbrains.kotlin.idea.formatter.kotlinCustomSettings
import org.jetbrains.kotlin.idea.util.projectStructure.allModules

class DiscordKtFacet(
    type: DiscordKtFacetType,
    module: Module,
    name: String,
    configuration: DiscordKtFacetConfiguration,
    underlyingFacet: Facet<*>?
) : Facet<DiscordKtFacetConfiguration>(
        type,
        module,
        name,
        configuration,
        underlyingFacet
) {

    init {
        Disposer.register(this, configuration)
    }

    companion object {
        fun get(project: Project): DiscordKtFacet? {
            for (module in project.allModules()) {
                val facet = FacetManager.getInstance(module)?.getFacetByType(DiscordKtFacetType.ID)
                if (facet != null) return facet
            }
            return null
        }
    }

    override fun initFacet() {
        try {
            val codeStyleSettings = CodeStyle.getDefaultSettings()
            val settings = codeStyleSettings.kotlinCustomSettings
            if (!settings.PACKAGES_TO_USE_STAR_IMPORTS.contains("discordkt")) {
                // PackageEntry(false, "discordkt", false)
                val entry = PackageEntry(true, "discordkt", true)
                settings.PACKAGES_TO_USE_STAR_IMPORTS.addEntry(entry)
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }
}