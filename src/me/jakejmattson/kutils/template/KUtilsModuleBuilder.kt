package me.jakejmattson.kutils.template

import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.*
import com.intellij.openapi.module.*
import com.intellij.openapi.options.*
import com.intellij.openapi.roots.*
import org.jetbrains.annotations.*

class KUtilsModuleBuilder : ModuleBuilder() {
    @Throws(ConfigurationException::class)
    override fun setupRootModel(model: ModifiableRootModel) {}

    override fun getModuleType() = KUtilsModuleType.instance

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable) = KUtilsWizardStep()
}