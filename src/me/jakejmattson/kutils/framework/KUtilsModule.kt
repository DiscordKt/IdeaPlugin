package me.jakejmattson.kutils.framework

import com.intellij.icons.AllIcons
import com.intellij.openapi.module.*

private const val ID = "KUtils"

class KUtilsModuleType : ModuleType<KUtilsWizard>(ID) {
    override fun getName()= "KUtils"

    override fun getDescription() = "A module for KUtils support"

    override fun createModuleBuilder() = KUtilsWizard()

    override fun getNodeIcon(b: Boolean) = AllIcons.General.Information

    companion object {
        fun getInstance() = ModuleTypeManager.getInstance().findByID(ID) as KUtilsModuleType
    }
}