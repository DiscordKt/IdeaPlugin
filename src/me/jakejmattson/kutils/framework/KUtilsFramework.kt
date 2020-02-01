package me.jakejmattson.kutils.framework

import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.*
import com.intellij.icons.AllIcons
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.*
import me.jakejmattson.kutils.module.KUtilsModuleType
import javax.swing.*

class KUtilsFramework : FrameworkTypeEx("") {
    override fun getPresentableName(): String {
        return "KUtils Framework"
    }

    override fun getIcon(): Icon {
        return AllIcons.General.Information
    }

    override fun createProvider(): FrameworkSupportInModuleProvider {
        return object : FrameworkSupportInModuleProvider() {
            override fun getFrameworkType(): FrameworkTypeEx {
                return this@KUtilsFramework
            }

            override fun createConfigurable(model: FrameworkSupportModel): FrameworkSupportInModuleConfigurable {
                return object : FrameworkSupportInModuleConfigurable() {
                    override fun createComponent(): JComponent? {
                        return JCheckBox("Some Extra Option")
                    }

                    override fun addSupport(module: Module, model: ModifiableRootModel, provider: ModifiableModelsProvider) {
                        // This is the place to set up a library, generate a specific file, etc
                        // and actually add framework support to a module.
                    }
                }
            }

            override fun isEnabledForModuleType(type: ModuleType<*>): Boolean {
                return type is KUtilsModuleType
            }
        }
    }
}