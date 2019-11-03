package me.jakejmattson.kutils.actions

import com.intellij.codeInsight.template.TemplateManager
import com.intellij.openapi.actionSystem.*
import me.jakejmattson.kutils.framework.Templates

class ServiceTemplateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val template = Templates(project).serviceTemplate

        TemplateManager.getInstance(project).startTemplate(editor, template)
    }
}
