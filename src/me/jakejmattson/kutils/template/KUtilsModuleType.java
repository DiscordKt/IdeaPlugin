package me.jakejmattson.kutils.template;

import com.intellij.icons.*;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.module.*;
import com.intellij.openapi.roots.ui.configuration.*;
import org.jetbrains.annotations.*;

import javax.swing.*;

public class KUtilsModuleType extends ModuleType<KUtilsModuleBuilder> {
    private static final String ID = "KUtils";

    public KUtilsModuleType() {
        super(ID);
    }

    public static KUtilsModuleType getInstance() {
        return (KUtilsModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public KUtilsModuleBuilder createModuleBuilder() {
        return new KUtilsModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "KUtils";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Create a KUtils bot project from a template.";
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return AllIcons.General.Information;
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull KUtilsModuleBuilder moduleBuilder, @NotNull ModulesProvider modulesProvider) {
        return super.createWizardSteps(wizardContext, moduleBuilder, modulesProvider);
    }
}