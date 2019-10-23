package me.jakejmattson.kutils.template;

import com.intellij.ide.util.projectWizard.*;

import javax.swing.*;

public class KUtilsWizardStep extends ModuleWizardStep {
    @Override
    public JComponent getComponent() {
        return new JLabel("Hello KUtils");
    }

    @Override
    public void updateDataModel() {
        //todo update model according to UI
    }
}

