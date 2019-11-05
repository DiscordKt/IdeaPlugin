package me.jakejmattson.kutils.framework

import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import java.awt.*
import java.io.File
import java.nio.file.Files
import javax.swing.*

private val chkProjectTemplate = JCheckBox("Project Template")
private val chkExtensiveExample = JCheckBox("Extensive Example")
private val buttons = listOf(chkProjectTemplate, chkExtensiveExample)

private val txtPackage = JTextField("me.your.organization.name")

class KUtilsModuleBuilder : ModuleBuilder() {
    override fun getModuleType() = KUtilsModuleType.getInstance()

    override fun createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider): Array<ModuleWizardStep> {
        return arrayOf(createProjectSetupStep())
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        when (buttons.firstOrNull { it.isSelected }) {
            chkProjectTemplate -> generateProject(modifiableRootModel)
            chkExtensiveExample -> generateExample(modifiableRootModel)
        }
    }
}

private fun generateProject(modifiableRootModel: ModifiableRootModel) {

    val project = modifiableRootModel.project
    val basePath = project.basePath
    val projectName = project.name.toLowerCase()
    val packagePath = txtPackage.text.split(".").joinToString("/")
    val packageStatement = txtPackage.text + "." + projectName

    val projectDir = File("$basePath/src/main/kotlin/$packagePath/$projectName")
    Files.createDirectories(projectDir.toPath())

    projectDir.createCommonDirectories()
    projectDir.createMainApp(packageStatement)
}

private fun generateExample(modifiableRootModel: ModifiableRootModel) {
    println("Generating Example")
}

private fun createProjectSetupStep() = object : ModuleWizardStep() {
    override fun getComponent() = JPanel().apply {
        layout = GridLayout(3, 1)

        addPanel {
            it.layout = GridLayout(2, 1)

            buttons.forEach { currentBox ->
                currentBox.addActionListener {
                    buttons.forEach {
                        if (it != currentBox)
                            it.isSelected = false
                    }
                }
                it.add(currentBox)
            }

            buttons.first().isSelected = true
        }

        addPanel {
            it.layout = FlowLayout()

            it.add(JLabel("GroupID: "))
            it.add(txtPackage)
        }
    }

    override fun updateDataModel() = Unit
}

private fun JPanel.addPanel(builder: (JPanel) -> Unit) {
    val panel = JPanel()
    builder.invoke(panel)
    add(panel)
}