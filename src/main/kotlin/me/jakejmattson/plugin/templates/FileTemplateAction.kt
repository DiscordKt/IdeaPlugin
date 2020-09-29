package me.jakejmattson.plugin.templates

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
import me.jakejmattson.plugin.utils.ICONS
import java.util.*

private const val ACTION_NAME = "DiscordKt File"

class FileTemplateAction : CreateFileFromTemplateAction(ACTION_NAME, "Creates new DiscordKt file", ICONS.DISCORDKT_16), DumbAware {
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
            .addKind("CommandSet", null, "DiscordKt CommandSet")
            .addKind("Service", null, "DiscordKt Service")
            .addKind("Data", null, "DiscordKt Data")
            .addKind("Precondition", null, "DiscordKt Precondition")
            .addKind("ArgumentType", null, "DiscordKt ArgumentType")
            .addKind("Conversation", null, "DiscordKt Conversation")
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String) = ACTION_NAME

    override fun isAvailable(dataContext: DataContext) =
        if (super.isAvailable(dataContext)) {
            val ideView = LangDataKeys.IDE_VIEW.getData(dataContext)!!
            val project = PlatformDataKeys.PROJECT.getData(dataContext)!!
            val projectFileIndex = ProjectRootManager.getInstance(project).fileIndex

            ideView.directories.any { projectFileIndex.isInSourceContent(it.virtualFile) }
        } else false

    override fun createFileFromTemplate(name: String, template: FileTemplate, dir: PsiDirectory): PsiFile? {
        val (className, targetDir) = findOrCreateTarget(dir, name)
        val attributes = AttributesDefaults(className).withFixedName(true)
        val properties = Properties(FileTemplateManager.getInstance(dir.project).defaultProperties)

        return CreateFromTemplateDialog(dir.project, targetDir, template, attributes, properties).create().containingFile
    }

    private object NameValidator : InputValidatorEx {
        override fun getErrorText(inputString: String): String? {
            val validIdentifier = "(?:\\b[_a-zA-Z]|\\B\\$)[_\$a-zA-Z0-9]*+".toRegex()

            return when {
                inputString.any { it.isWhitespace() } -> "Name cannot contain whitespace"
                !inputString.matches(validIdentifier) -> "Invalid identifier"
                else -> null
            }
        }

        override fun checkInput(inputString: String) = true
        override fun canClose(inputString: String) = getErrorText(inputString) == null
    }

    private fun findOrCreateTarget(dir: PsiDirectory, name: String): Pair<String, PsiDirectory> {
        val fileName = name.removeSuffix(".kt")
        val splitChar = DIR_SEPARATORS.firstOrNull { it in fileName } ?: return fileName to dir
        val names = fileName.trim().split(splitChar)
        val targetDir = names
            .dropLast(1)
            .fold(dir) { psiDirectory: PsiDirectory, dirName: String ->
                psiDirectory.findSubdirectory(dirName) ?: runWriteAction {
                    psiDirectory.createSubdirectory(dirName)
                }
            }

        return names.last() to targetDir
    }

    private val DIR_SEPARATORS = charArrayOf('/', '\\', '.')
}