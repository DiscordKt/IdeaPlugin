package me.jakejmattson.kutils.templates

import com.intellij.codeInsight.template.TemplateManager
import com.intellij.openapi.actionSystem.*

class ServiceTemplateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val template = Templates(project).serviceTemplate

        TemplateManager.getInstance(project).startTemplate(editor, template)
    }
}
