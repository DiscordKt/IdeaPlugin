package me.jakejmattson.kutils.template;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.*;
import com.intellij.openapi.module.*;
import com.intellij.openapi.options.*;
import com.intellij.openapi.roots.*;
import org.jetbrains.annotations.*;

public class KUtilsModuleBuilder extends ModuleBuilder {
    @Override
    public void setupRootModel(ModifiableRootModel model) throws ConfigurationException {

    }

    @Override
    public ModuleType getModuleType() {
        return KUtilsModuleType.getInstance();
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new KUtilsWizardStep();
    }
}