package me.jakejmattson.kutils.framework

import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.*
import com.intellij.icons.AllIcons
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.*
import javax.swing.*

private val chkProjectTemplate = JCheckBox("Project Template")

private val buttons = listOf(chkProjectTemplate, JCheckBox("Extensive Example"))

class KUtilsFramework : FrameworkTypeEx("me.jakejmattson.kutils.framework.KUtilsFramework") {
    override fun createProvider() = object : FrameworkSupportInModuleProvider() {
        override fun getFrameworkType() = this@KUtilsFramework

        override fun createConfigurable(model: FrameworkSupportModel): FrameworkSupportInModuleConfigurable {
            return object : FrameworkSupportInModuleConfigurable() {
                override fun createComponent() =
                    JPanel().apply {
                        layout = BoxLayout(this, 1)

                        buttons.forEach { currentBox ->
                            currentBox.addActionListener {
                                buttons.forEach {
                                    if (it != currentBox)
                                        it.isSelected = false
                                }
                            }
                            add(currentBox)
                        }

                        buttons.first().isSelected = true
                    }

                override fun addSupport(module: Module, model: ModifiableRootModel, provider: ModifiableModelsProvider) {
                    val button = buttons.firstOrNull { it.isSelected }
                    println(button?.name)
                }
            }
        }

        override fun isEnabledForModuleType(type: ModuleType<*>) = true
    }

    override fun getPresentableName() = "KUtils"

    override fun getIcon() = AllIcons.General.Information!!
}