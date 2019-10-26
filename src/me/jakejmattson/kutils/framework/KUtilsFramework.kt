package me.jakejmattson.kutils.framework

import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.*
import com.intellij.icons.AllIcons
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.*
import javax.swing.*

class KUtilsFramework : FrameworkTypeEx("me.jakejmattson.kutils.framework.KUtilsFramework") {
    override fun createProvider() = object : FrameworkSupportInModuleProvider() {
        override fun getFrameworkType() = this@KUtilsFramework

        override fun createConfigurable(model: FrameworkSupportModel): FrameworkSupportInModuleConfigurable {
            return object : FrameworkSupportInModuleConfigurable() {
                override fun createComponent() =
                    JPanel().apply {
                        this.layout = BoxLayout(this, 1)
                        this.add(JCheckBox("Project Template"))
                        this.add(JCheckBox("Extensive Example"))
                    }

                override fun addSupport(module: Module, model: ModifiableRootModel, provider: ModifiableModelsProvider) {

                }
            }
        }

        override fun isEnabledForModuleType(type: ModuleType<*>) = true
    }

    override fun getPresentableName() = "KUtils"

    override fun getIcon() = AllIcons.General.Information!!
}