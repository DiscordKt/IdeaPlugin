package me.jakejmattson.kutils.framework

import com.intellij.codeInsight.template.TemplateManager
import com.intellij.openapi.project.Project

class Templates(project: Project) {
    val serviceTemplate = TemplateManager.getInstance(project).createTemplate("Service", "KUtils",
        """
            #if ($\{PACKAGE_NAME} != "")package $\{PACKAGE_NAME};#end
            
            import me.aberrantfox.kjdautils.api.annotation.Service
            
            @Service
            class $\{NAME} {

            }
        """.trimIndent()
    )
}