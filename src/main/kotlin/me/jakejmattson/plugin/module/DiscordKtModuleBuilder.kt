package me.jakejmattson.plugin.module

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.SourcePathsBuilder
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.util.Pair
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class DiscordKtModuleBuilder : ModuleBuilder(), SourcePathsBuilder {
    private var mySourcePaths: MutableList<Pair<String, String>>? = null
    private val myModuleLibraries: List<Pair<String, String>> = ArrayList()
    private val myCompilerOutputPath: String? = null

    @Throws(ConfigurationException::class)
    override fun setupRootModel(rootModel: ModifiableRootModel) {
        val compilerModuleExtension = rootModel.getModuleExtension(CompilerModuleExtension::class.java)
        compilerModuleExtension.isExcludeOutput = true
        rootModel.inheritSdk()
        val contentEntry = doAddContentEntry(rootModel)
        if (contentEntry != null) {
            val sourcePaths: List<Pair<String, String>> = sourcePaths
            for (sourcePath in sourcePaths) {
                val first = sourcePath.first
                File(first).mkdirs()
                val sourceRoot = LocalFileSystem.getInstance().refreshAndFindFileByPath(FileUtil.toSystemIndependentName(first))
                if (sourceRoot != null) {
                    contentEntry.addSourceFolder(sourceRoot, false, sourcePath.second)
                }
            }
        }
        if (myCompilerOutputPath != null) {
            // should set only absolute paths
            val canonicalPath: String? = try {
                FileUtil.resolveShortWindowsName(myCompilerOutputPath)
            } catch (e: IOException) {
                myCompilerOutputPath
            }
            compilerModuleExtension
                .setCompilerOutputPath(VfsUtil.pathToUrl(FileUtil.toSystemIndependentName(canonicalPath!!)))
        } else {
            compilerModuleExtension.inheritCompilerOutputPath(true)
        }
        val libraryTable = rootModel.moduleLibraryTable
        for (libInfo in myModuleLibraries) {
            val moduleLibraryPath = libInfo.first
            val sourceLibraryPath = libInfo.second
            val library = libraryTable.createLibrary()
            val modifiableModel = library.modifiableModel
            modifiableModel.addRoot(getUrlByPath(moduleLibraryPath), OrderRootType.CLASSES)
            if (sourceLibraryPath != null) {
                modifiableModel.addRoot(getUrlByPath(sourceLibraryPath), OrderRootType.SOURCES)
            }
            modifiableModel.commit()
        }
    }

    override fun getModuleType(): ModuleType<DiscordKtModuleBuilder> {
        return DiscordKtModuleType.instance
    }

    override fun isTemplateBased(): Boolean {
        return true
    }

    override fun isTemplate(): Boolean {
        return false
    }

    @Throws(ConfigurationException::class)
    override fun getSourcePaths(): List<Pair<String, String>> {
        val mySourcePaths = mySourcePaths

        if (mySourcePaths == null) {
            val paths: MutableList<Pair<String, String>> = ArrayList()
            val src = Paths.get(contentEntryPath).resolve("src/main/kotlin")
            try {
                Files.createDirectories(src)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            paths.add(Pair.create(src.toString(), ""))
            return paths
        }
        return mySourcePaths
    }

    override fun setSourcePaths(sourcePaths: List<Pair<String, String>>) {
        mySourcePaths = ArrayList(sourcePaths)
    }

    override fun addSourcePath(sourcePathInfo: Pair<String, String>) {
        if (mySourcePaths == null) {
            mySourcePaths = ArrayList()
        }
        mySourcePaths!!.add(sourcePathInfo)
    }

    companion object {
        private fun getUrlByPath(path: String): String {
            return VfsUtil.getUrlForLibraryRoot(File(path))
        }
    }
}