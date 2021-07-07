package me.jakejmattson.plugin.framework

import com.intellij.facet.FacetManager
import com.intellij.ide.util.frameworkSupport.FrameworkSupportConfigurable
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.libraries.Library
import me.jakejmattson.plugin.facet.DiscordKtFacet
import me.jakejmattson.plugin.facet.DiscordKtFacetConfiguration
import me.jakejmattson.plugin.facet.DiscordKtFacetType
import javax.swing.JLabel

class DiscordKtFrameworkSupportConfigurable(model: FrameworkSupportModel) : FrameworkSupportConfigurable() {
    override fun getComponent() = JLabel("DiscordKt Framework Support Configuration")

    override fun addSupport(module: Module, modifiableRootModel: ModifiableRootModel, library: Library?) {
        val facetManager = FacetManager.getInstance(module)
        val facetModel = facetManager.createModifiableModel()
        val facet = FacetManager.getInstance(modifiableRootModel.module).addFacet<DiscordKtFacet, DiscordKtFacetConfiguration>(DiscordKtFacetType.INSTANCE, "DiscordKt", null)
        facetModel.addFacet(facet)
        facetModel.commit()
    }

    init {
        model.setFrameworkComponentEnabled("DiscordKt", true)
    }
}