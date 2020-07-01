package me.jakejmattson.kutils.framework

import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.*
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.*
import me.jakejmattson.kutils.utils.ICONS
import javax.swing.JComponent

private const val FRAMEWORK_ID = "me.jakejmattson.kutils.framework.KUtilsFramework"

class KUtilsFramework : FrameworkTypeEx(FRAMEWORK_ID) {
    override fun createProvider() =
        object : FrameworkSupportInModuleProvider() {
            override fun getFrameworkType() = this@KUtilsFramework

            override fun createConfigurable(model: FrameworkSupportModel) =
                object : FrameworkSupportInModuleConfigurable() {
                    override fun createComponent(): JComponent? = null

                    override fun addSupport(module: Module, model: ModifiableRootModel, provider: ModifiableModelsProvider) {
                        //TODO Add KUtils support
                    }
                }

            override fun isEnabledForModuleType(type: ModuleType<*>) = true
        }

    override fun getPresentableName() = "KUtils Framework"
    override fun getIcon() = ICONS.KUTILS_16
}