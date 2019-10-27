package me.jakejmattson.kutils.framework

import com.intellij.icons.AllIcons
import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import javax.swing.*

private val chkProjectTemplate = JCheckBox("Project Template")
private val chkExtensiveExample = JCheckBox("Extensive Example")

private val buttons = listOf(chkProjectTemplate, chkExtensiveExample)

class KUtilsWizard : ModuleBuilder() {
    override fun getModuleType() = object : ModuleType<KUtilsWizard>("me.jakejmattson.kutils.framework.KUtilsWizard") {
        override fun createModuleBuilder() = KUtilsWizard()
        override fun getName() = "KUtils"
        override fun getDescription() = "Create a project from a KUtils template."
        override fun getNodeIcon(b: Boolean) = AllIcons.General.Information!!
    }

    override fun createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider): Array<ModuleWizardStep> {
        return arrayOf(createProjectSelectionStep())
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        when (buttons.firstOrNull { it.isSelected }) {
            chkProjectTemplate -> generateProject(modifiableRootModel)
            chkExtensiveExample -> generateExample(modifiableRootModel)
        }
    }
}

private fun generateProject(modifiableRootModel: ModifiableRootModel) {
    println("Generating Project")
}

private fun generateExample(modifiableRootModel: ModifiableRootModel) {
    println("Generating Example")
}

private fun createProjectSelectionStep() = object : ModuleWizardStep() {
    override fun getComponent() = JPanel().apply {
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

    override fun updateDataModel() { }
}