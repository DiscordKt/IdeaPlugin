package me.jakejmattson.kutils.template

import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.externalSystem.ExternalSystemManager
import com.intellij.openapi.externalSystem.model.Key
import com.intellij.openapi.externalSystem.model.project.ExternalEntityData
import com.intellij.openapi.externalSystem.service.project.ProjectDataManager
import com.intellij.openapi.externalSystem.service.project.manage.*

import javax.swing.*

class KUtilsWizardStep : ModuleWizardStep() {
    override fun getComponent(): JComponent {
        return JLabel()
    }

    override fun updateDataModel() {
        //todo update model according to UI
    }
}