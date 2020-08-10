package me.jakejmattson.discordkt.framework

import com.intellij.framework.FrameworkTypeEx
import com.intellij.framework.addSupport.*
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.*
import me.jakejmattson.discordkt.module.DiscordKtModuleType
import me.jakejmattson.discordkt.utils.ICONS
import javax.swing.JComponent

private const val FRAMEWORK_ID = "me.jakejmattson.discordkt.framework.DiscordKtFramework"

class DiscordKtFramework : FrameworkTypeEx(FRAMEWORK_ID) {
    override fun createProvider() =
        object : FrameworkSupportInModuleProvider() {
            override fun getFrameworkType() = this@DiscordKtFramework

            override fun createConfigurable(model: FrameworkSupportModel) =
                object : FrameworkSupportInModuleConfigurable() {
                    override fun createComponent(): JComponent? = null

                    override fun addSupport(module: Module, model: ModifiableRootModel, provider: ModifiableModelsProvider) {
                        //TODO Add DiscordKt support
                    }
                }

            override fun isEnabledForModuleType(type: ModuleType<*>) = type is DiscordKtModuleType
        }

    override fun getPresentableName() = "DiscordKt Framework"
    override fun getIcon() = ICONS.DISCORDKT_16
}