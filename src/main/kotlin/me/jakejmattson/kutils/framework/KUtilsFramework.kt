package me.jakejmattson.kutils.framework

import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.*
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.*
import me.jakejmattson.kutils.utils.ICONS
import javax.swing.*

class KUtilsFramework : FrameworkTypeEx(FRAMEWORK_ID) {
    override fun createProvider(): FrameworkSupportInModuleProvider {
        return object : FrameworkSupportInModuleProvider() {
            override fun getFrameworkType() = this@KUtilsFramework

            override fun createConfigurable(model: FrameworkSupportModel): FrameworkSupportInModuleConfigurable {
                return object : FrameworkSupportInModuleConfigurable() {
                    override fun createComponent(): JComponent? {
                        return JCheckBox("SDK Extra Option")
                    }

                    override fun addSupport(module: Module, model: ModifiableRootModel, provider: ModifiableModelsProvider) {
                        // This is the place to set up a library, generate a specific file, etc
                        // and actually add framework support to a module.
                    }
                }
            }

            override fun isEnabledForModuleType(type: ModuleType<*>) = true
        }
    }

    override fun getPresentableName() = "KUtils Framework"

    override fun getIcon() = ICONS.KUTILS_16

    companion object {
        const val FRAMEWORK_ID = "me.jakejmattson.kutils.framework.KUtilsFramework"
    }
}