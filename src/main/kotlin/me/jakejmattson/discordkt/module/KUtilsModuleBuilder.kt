package me.jakejmattson.discordkt.module

import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.Disposable
import com.intellij.openapi.roots.ModifiableRootModel
import javax.swing.JLabel

class DiscordKtModuleBuilder : ModuleBuilder() {
    override fun setupRootModel(model: ModifiableRootModel) {}
    override fun getModuleType() = DiscordKtModuleType.getInstance()
    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable) = DiscordKtModuleWizardStep()
}

class DiscordKtModuleWizardStep : ModuleWizardStep() {
    override fun getComponent() = JLabel("Provide some setting here")

    override fun updateDataModel() {
        println("Updating")
    }
}