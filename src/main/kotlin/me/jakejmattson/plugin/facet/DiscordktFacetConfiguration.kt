package me.jakejmattson.plugin.facet

import com.intellij.facet.FacetConfiguration
import com.intellij.facet.ui.FacetEditorContext
import com.intellij.facet.ui.FacetValidatorsManager
import com.intellij.openapi.Disposable
import org.jdom.Element

class DiscordKtFacetConfiguration : FacetConfiguration, Disposable {
    override fun createEditorTabs(editorContext: FacetEditorContext, validatorsManager: FacetValidatorsManager) =
        arrayOf(DiscordKtFacetEditorTab(editorContext))

    override fun readExternal(element: Element?) {}
    override fun writeExternal(element: Element?) {}
    override fun dispose() {}
}