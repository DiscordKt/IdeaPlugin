package me.jakejmattson.kutils.facet

import com.intellij.facet.*
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.openapi.module.*
import me.jakejmattson.kutils.utils.ICONS

class KUtilsFacetType : FacetType<KUtilsFacet, KUtilsFacetConfiguration>(KUTILS_FACET_TYPE_ID, FACET_ID, FACET_NAME) {
    override fun createDefaultConfiguration(): KUtilsFacetConfiguration {
        return KUtilsFacetConfiguration()
    }

    override fun isSuitableModuleType(type: ModuleType<ModuleBuilder>?): Boolean {
        return true
    }

    override fun getIcon() = ICONS.KUTILS_16

    companion object {
        const val FACET_ID = "KUtils_FACET_ID"
        const val FACET_NAME = "KUtils Facet"
        val KUTILS_FACET_TYPE_ID = FacetTypeId<KUtilsFacet>(FACET_ID)
    }

    override fun createFacet(module: Module, name: String, configuration: KUtilsFacetConfiguration, underlyingFacet: Facet<*>?): KUtilsFacet? {
        underlyingFacet as? Facet<KUtilsFacetConfiguration> ?: return null
        
        return KUtilsFacet(this, module, name, configuration, underlyingFacet)
    }
}