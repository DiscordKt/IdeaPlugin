package me.jakejmattson.kutils.templates

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.actions.AttributesDefaults
import com.intellij.ide.fileTemplates.ui.CreateFromTemplateDialog
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.util.IncorrectOperationException
import java.util.*

private const val ACTION_NAME = "KUtils File"

class FileTemplateAction : CreateFileFromTemplateAction(ACTION_NAME, "Creates new KUtils file", null), DumbAware {

    override fun postProcess(createdElement: PsiFile, templateName: String?, customProperties: Map<String, String>?) {
        super.postProcess(createdElement, templateName, customProperties)

        val editor = FileEditorManager.getInstance(createdElement.project).selectedTextEditor ?: return
        if (editor.document == createdElement.viewProvider.document) {
            val lineCount = editor.document.lineCount
            if (lineCount > 0) {
                editor.caretModel.moveToLogicalPosition(LogicalPosition(lineCount - 1, 0))
            }
        }
    }

    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        registerTemplates(project)

        builder.setTitle("New $ACTION_NAME")
                .setValidator(NameValidator)
                .addKind("CommandSet", null, "KUtils CommandSet")
                .addKind("Service", null, "KUtils Service")
                .addKind("Data", null, "KUtils Data")
                .addKind("Precondition", null, "KUtils Precondition")
                .addKind("ArgumentType", null, "KUtils ArgumentType")
                .addKind("Conversation", null, "KUtils Conversation")
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String) = ACTION_NAME

    override fun isAvailable(dataContext: DataContext): Boolean {
        if (super.isAvailable(dataContext)) {
            val ideView = LangDataKeys.IDE_VIEW.getData(dataContext)!!
            val project = PlatformDataKeys.PROJECT.getData(dataContext)!!
            val projectFileIndex = ProjectRootManager.getInstance(project).fileIndex
            return ideView.directories.any { projectFileIndex.isInSourceContent(it.virtualFile) }
        }

        return false
    }

    override fun createFileFromTemplate(name: String, template: FileTemplate, dir: PsiDirectory): PsiFile? {
        val directorySeparators = if (template.name == "Kotlin File") FILE_SEPARATORS else FQNAME_SEPARATORS
        val (className, targetDir) = findOrCreateTarget(dir, name, directorySeparators)

        val service = DumbService.getInstance(dir.project)
        service.isAlternativeResolveEnabled = true
        try {
            return createFromTemplate(targetDir, className, template)
        } finally {
            service.isAlternativeResolveEnabled = false
        }
    }

    companion object {
        private object NameValidator : InputValidatorEx {
            override fun getErrorText(inputString: String): String? {
                if (inputString.trim().isEmpty()) {
                    return "Name can't be empty"
                }

                val parts: List<String> = inputString.split(*FQNAME_SEPARATORS)
                if (parts.any { it.trim().isEmpty() }) {
                    return "Name can't have empty parts"
                }

                return null
            }

            override fun checkInput(inputString: String) = true
            override fun canClose(inputString: String) = getErrorText(inputString) == null
        }

        private fun findOrCreateTarget(dir: PsiDirectory, name: String, directorySeparators: CharArray): Pair<String, PsiDirectory> {
            var className = removeKotlinExtension(name)
            var targetDir = dir

            for (splitChar in directorySeparators) {
                if (splitChar in className) {
                    val names = className.trim().split(splitChar)

                    for (dirName in names.dropLast(1)) {
                        targetDir = targetDir.findSubdirectory(dirName) ?: runWriteAction {
                            targetDir.createSubdirectory(dirName)
                        }
                    }

                    className = names.last()
                    break
                }
            }

            return Pair(className, targetDir)
        }

        private fun removeKotlinExtension(name: String) = name.removeSuffix(".kt")

        private fun createFromTemplate(dir: PsiDirectory, className: String, template: FileTemplate) =
                try {
                    val project = dir.project
                    val defaultProperties = FileTemplateManager.getInstance(project).defaultProperties
                    val properties = Properties(defaultProperties)
                    val attributes = AttributesDefaults(className).withFixedName(true)

                    CreateFromTemplateDialog(project, dir, template, attributes, properties).create().containingFile
                } catch (e: IncorrectOperationException) {
                    throw e
                } catch (e: Exception) {
                    LOG.error(e)
                    null
                }

        private val FILE_SEPARATORS = charArrayOf('/', '\\')
        private val FQNAME_SEPARATORS = charArrayOf('/', '\\', '.')
    }
}