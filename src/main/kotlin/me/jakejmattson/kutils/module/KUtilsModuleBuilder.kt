package me.jakejmattson.kutils.module

import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.Disposable
import com.intellij.openapi.roots.ModifiableRootModel
import javax.swing.JLabel

class KUtilsModuleBuilder : ModuleBuilder() {
    override fun setupRootModel(model: ModifiableRootModel) {}
    override fun getModuleType() = KUtilsModuleType.getInstance()
    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable) = KUtilsModuleWizardStep()
}

class KUtilsModuleWizardStep : ModuleWizardStep() {
    override fun getComponent() = JLabel("Provide some setting here")

    override fun updateDataModel() {
        println("Updating")
    }
}