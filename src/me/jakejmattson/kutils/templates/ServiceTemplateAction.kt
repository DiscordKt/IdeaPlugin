package me.jakejmattson.kutils.templates

import com.intellij.ide.actions.*
import com.intellij.ide.fileTemplates.*
import com.intellij.ide.fileTemplates.actions.AttributesDefaults
import com.intellij.ide.fileTemplates.ui.CreateFromTemplateDialog
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.*
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.psi.*
import com.intellij.util.IncorrectOperationException
import java.util.Properties

private const val ACTION_NAME = "KUtils File"

class ServiceTemplateAction : CreateFileFromTemplateAction(ACTION_NAME, "Creates new KUtils file", null), DumbAware {

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
        val templates = Templates(project)

        builder.setTitle("New KUtils File")
            .addKind("Service", null, templates.serviceTemplate.key)
            .addKind("Something else", null, "Kotlin file")

        builder.setValidator(NameValidator)
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

    override fun startInWriteAction() = false

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

            override fun checkInput(inputString: String): Boolean {
                return true
            }

            override fun canClose(inputString: String): Boolean {
                return getErrorText(inputString) == null
            }
        }

        private fun findOrCreateTarget(dir: PsiDirectory, name: String, directorySeparators: CharArray): Pair<String, PsiDirectory> {
            var className = removeKotlinExtensionIfPresent(name)
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

        private fun removeKotlinExtensionIfPresent(name: String): String = when {
            name.endsWith(".kt") -> name.removeSuffix(".kt")
            else -> name
        }

        private fun createFromTemplate(dir: PsiDirectory, className: String, template: FileTemplate): PsiFile? {
            val project = dir.project
            val defaultProperties = FileTemplateManager.getInstance(project).defaultProperties

            val properties = Properties(defaultProperties)

            val element = try {
                CreateFromTemplateDialog(
                    project, dir, template,
                    AttributesDefaults(className).withFixedName(true),
                    properties
                ).create()
            } catch (e: IncorrectOperationException) {
                throw e
            } catch (e: Exception) {
                LOG.error(e)
                return null
            }

            return element?.containingFile
        }

        private val FILE_SEPARATORS = charArrayOf('/', '\\')
        private val FQNAME_SEPARATORS = charArrayOf('/', '\\', '.')
    }
}