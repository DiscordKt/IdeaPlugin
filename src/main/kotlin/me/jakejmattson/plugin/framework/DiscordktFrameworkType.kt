package me.jakejmattson.plugin.framework

import com.intellij.facet.FacetManager
import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.FrameworkSupportInModuleConfigurable
import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableModelsProvider
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.FacetsProvider
import me.jakejmattson.plugin.facet.DiscordKtFacet
import me.jakejmattson.plugin.facet.DiscordKtFacetType
import me.jakejmattson.plugin.module.DiscordKtModuleType
import me.jakejmattson.plugin.utils.ICONS
import javax.swing.Icon

class DiscordKtFrameworkType : FrameworkTypeEx("DiscordKt") {
    override fun getIcon(): Icon = ICONS.DISCORDKT_16
    override fun getPresentableName() = id

    override fun createProvider() = object : FrameworkSupportInModuleProvider() {
        override fun getFrameworkType() = this@DiscordKtFrameworkType
        override fun isEnabledForModuleType(moduleType: ModuleType<*>) = moduleType is DiscordKtModuleType

        override fun isSupportAlreadyAdded(module: Module, facetsProvider: FacetsProvider) =
            facetsProvider.getAllFacets(module).find { it is DiscordKtFacet } != null

        override fun canAddSupport(module: Module, facetsProvider: FacetsProvider) =
            !isSupportAlreadyAdded(module, facetsProvider)

        override fun createConfigurable(model: FrameworkSupportModel) = object : FrameworkSupportInModuleConfigurable() {
            override fun addSupport(module: Module, rootModel: ModifiableRootModel, modifiableModelsProvider: ModifiableModelsProvider) {
                val facetManager = FacetManager.getInstance(module)
                val facetModel = facetManager.createModifiableModel()
                val facet = facetManager.addFacet(DiscordKtFacetType.INSTANCE, "DiscordKt", null)
                facetModel.addFacet(facet)
                facetModel.commit()
            }

            override fun createComponent() = null
        }
    }
}