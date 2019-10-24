package me.jakejmattson.kutils.template

import com.intellij.icons.*
import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.ui.configuration.*
import org.jetbrains.annotations.*

import javax.swing.*

private const val ID = "KUtils"
private const val moduleDescription = "Create a KUtils bot project from a template."

class KUtilsModuleType : ModuleType<KUtilsModuleBuilder>(ID) {

    override fun createModuleBuilder() = KUtilsModuleBuilder()

    override fun getName() = "KUtils"

    override fun getDescription() = moduleDescription

    override fun getNodeIcon(boolean: Boolean) = AllIcons.General.Information!!

    companion object {
        val instance: KUtilsModuleType
            get() = ModuleTypeManager.getInstance().findByID(ID) as KUtilsModuleType
    }
}