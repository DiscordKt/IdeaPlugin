package me.jakejmattson.kutils.facet

import com.intellij.facet.ui.*
import com.intellij.openapi.util.Comparing
import org.jetbrains.annotations.Nls
import java.awt.BorderLayout
import javax.swing.*


/**
 * Provides the JPanel to be displayed in the facet UI.
 * Manages validation and modification of the [KUtilsFacet] state.
 */
class KUtilsFacetEditorTab(state: KUtilsFacetState, context: FacetEditorContext?, validator: FacetValidatorsManager?) : FacetEditorTab() {
    private val mySettings: KUtilsFacetState
    private val myPath: JTextField

    /**
     * Provides the JPanel displayed in the Preferences | Facet UI
     *
     * @return JPanel to be displayed in the org.intellij.sdk.facet.KUtilsFacetEditorTab.
     */
    override fun createComponent(): JComponent {
        val top = JPanel(BorderLayout())
        top.add(JLabel(FACET_PANEL_PROMPT), BorderLayout.WEST)
        top.add(myPath)
        val facetPanel = JPanel(BorderLayout())
        facetPanel.add(top, BorderLayout.NORTH)
        return facetPanel
    }

    /**
     * @return the name of this facet for display in this editor tab.
     */
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String? {
        return KUtilsFacetType.FACET_NAME
    }

    /**
     * Determines if the facet state entered in the UI differs
     * from the currently stored state.
     * Called when user changes text in myPath.
     *
     * @return `true` if the state returned from the panel's UI
     * differs from the stored facet state.
     */
    override fun isModified(): Boolean {
        return !Comparing.equal(mySettings.kutilsFacetState, myPath.text.trim { it <= ' ' })
    }

    override fun apply() {
        mySettings.kutilsFacetState = myPath.text
    }

    /**
     * Copies current org.intellij.sdk.facet.KUtilsFacetState into the myPath UI element.
     * This method is called each time this editor tab is needed for display.
     */
    override fun reset() {
        myPath.text = mySettings.kutilsFacetState
    }

    companion object {
        private const val FACET_PANEL_PROMPT = "Path To SDK: "
    }

    /**
     * Only org.intellij.sdk.facet.KUtilsFacetState is captured so it can be updated per user changes
     * in the EditorTab.
     *
     * @param state     org.intellij.sdk.facet.KUtilsFacetState object persisting org.intellij.sdk.facet.KUtilsFacet state.
     * @param context   Facet editor context, can be used to get e.g. the current project module.
     * @param validator Facet validator manager, can be used to get and apply a custom validator for
     * this facet.
     */
    init {
        mySettings = state
        myPath = JTextField(state.kutilsFacetState)
    }
}