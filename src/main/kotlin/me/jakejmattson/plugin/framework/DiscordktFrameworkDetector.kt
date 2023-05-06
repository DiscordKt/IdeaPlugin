package me.jakejmattson.plugin.framework

import com.intellij.framework.detection.DetectedFrameworkDescription
import com.intellij.framework.detection.FacetBasedFrameworkDetector
import com.intellij.framework.detection.FileContentPattern
import com.intellij.framework.detection.FrameworkDetectionContext
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.StandardPatterns
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.util.indexing.FileContent
import me.jakejmattson.plugin.facet.DiscordKtFacet
import me.jakejmattson.plugin.facet.DiscordKtFacetConfiguration
import me.jakejmattson.plugin.facet.DiscordKtFacetType
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.KtFile
import java.util.Collections

class DiscordKtFrameworkDetector :
    FacetBasedFrameworkDetector<DiscordKtFacet, DiscordKtFacetConfiguration>("DiscordKt", 1) {
    override fun getFacetType() = DiscordKtFacetType.INSTANCE

    override fun createSuitableFilePattern(): ElementPattern<FileContent> {
        return FileContentPattern.fileContent().withName(StandardPatterns.string().endsWith(".kt"))
    }

    override fun createConfiguration(files: MutableCollection<out VirtualFile>?): DiscordKtFacetConfiguration? =
        facetType.createDefaultConfiguration()

    override fun getFileType(): KotlinFileType = KotlinFileType.INSTANCE

    override fun detect(
        newFiles: MutableCollection<out VirtualFile>,
        context: FrameworkDetectionContext
    ): MutableList<out DetectedFrameworkDescription> {
        val project = context.project ?: return Collections.emptyList()
        val psiManager = PsiManager.getInstance(project)

        for (file in newFiles) {
            val psiFile: PsiFile? = psiManager.findFile(file)
            if (psiFile is KtFile && containsDiscordKtImports(psiFile))
                return context.createDetectedFacetDescriptions(this, mutableListOf(file))
        }

        return Collections.emptyList()
    }
}

fun containsDiscordKtImports(file: KtFile) = file.importList?.imports?.find {
    it.importedFqName?.asString()?.contains("discordkt") ?: false
} != null