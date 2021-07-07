package me.jakejmattson.plugin.facet

import com.intellij.facet.ui.FacetEditorContext
import com.intellij.facet.ui.FacetEditorTab
import javax.swing.JLabel

class DiscordKtFacetEditorTab(val editorContext: FacetEditorContext) : FacetEditorTab() {
    override fun isModified() = false
    override fun getDisplayName() = "DiscordKt"
    override fun createComponent() = JLabel("No configuration options yet.")
}