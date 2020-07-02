package me.jakejmattson.kutils.module

import com.intellij.openapi.module.*
import me.jakejmattson.kutils.utils.ICONS

private const val ID = "KUTILS_MODULE"

class KUtilsModuleType : ModuleType<KUtilsModuleBuilder>(ID) {
    override fun getName() = "KUtils"
    override fun getDescription() = "KUtils module type"
    override fun getNodeIcon(b: Boolean) = ICONS.KUTILS_16
    override fun createModuleBuilder() = KUtilsModuleBuilder()

    companion object {
        fun getInstance() = ModuleTypeManager.getInstance().findByID(ID) as KUtilsModuleType
    }
}