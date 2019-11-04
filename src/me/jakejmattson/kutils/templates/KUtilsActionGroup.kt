package me.jakejmattson.kutils.templates

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup

class KUtilsActionGroup : DefaultActionGroup() {
    override fun update(event: AnActionEvent) {
        event.presentation.icon = AllIcons.General.Information
    }
}