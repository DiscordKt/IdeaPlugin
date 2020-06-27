package me.jakejmattson.kutils.facet

import com.intellij.facet.FacetConfiguration
import com.intellij.facet.ui.*
import com.intellij.openapi.components.PersistentStateComponent


class KUtilsFacetConfiguration : FacetConfiguration, PersistentStateComponent<KUtilsFacetState> {
    // Manages the data stored with this facet.
    private var myFacetState: KUtilsFacetState = KUtilsFacetState()

    /**
     * Called by the IntelliJ Platform when saving this facet's state persistently.
     * @return a component state. All properties, public and annotated fields are serialized.
     * Only values which differ from default (i.e. the value of newly instantiated class) are serialized.
     * `null` value indicates that the returned state won't be stored, and
     * as a result previously stored state will be used.
     */
    override fun getState(): KUtilsFacetState {
        return myFacetState
    }

    /**
     * Called by the IntelliJ Platform when this facet's state is loaded.
     * The method can and will be called several times, if
     * config files were externally changed while IDEA running.
     */
    override fun loadState(state: KUtilsFacetState) {
        myFacetState = state
    }

    /**
     * Creates a set of editor tabs for this facet, potentially one per context.
     *
     * @param context The context in which a facet is being added/deleted, or modified.
     * @param manager The manager which can be used to access custom validators.
     * @return Array of KUtilsFacetEditorTabs. In this case size is always 1.
     */
    override fun createEditorTabs(context: FacetEditorContext, manager: FacetValidatorsManager): Array<FacetEditorTab> {
        return arrayOf(
                KUtilsFacetEditorTab(myFacetState, context, manager)
        )
    }
}