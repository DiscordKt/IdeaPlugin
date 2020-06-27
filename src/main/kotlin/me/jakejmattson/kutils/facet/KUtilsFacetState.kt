package me.jakejmattson.kutils.facet

class KUtilsFacetState internal constructor() {
    var kutilsFacetState: String? = null

    companion object {
        const val KUtils_FACET_INIT_PATH = ""
    }

    init {
        kutilsFacetState = KUtils_FACET_INIT_PATH
    }
}