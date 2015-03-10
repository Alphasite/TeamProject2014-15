package uk.ac.gla.teamL.execution.configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 12:14
 */
public class EBNFRunConfigurationUI extends SettingsEditor<EBNFRunConfiguration> {
    JCheckBox antlr;
    JCheckBox yacc;
    JCheckBox diagram;
    TextFieldWithBrowseButton fileSelector;

    /**
     * Constructs a new frame that is initially invisible.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public EBNFRunConfigurationUI() throws HeadlessException {
        antlr = new JCheckBox("Generate Antlr Grammar");
        yacc = new JCheckBox("Generate YACC Grammar.");
        diagram = new JCheckBox("Generate Railroad Diagram");
        fileSelector = new TextFieldWithBrowseButton();
    }

    @Override
    protected void resetEditorFrom(EBNFRunConfiguration config) {
        this.antlr.setSelected(config.isGenerateAntlr());
        this.yacc.setSelected(config.isGenerateYacc());
        this.diagram.setSelected(config.isGenerateRRDiagram());
        this.fileSelector.setText(config.getFilePath());
    }

    @Override
    protected void applyEditorTo(EBNFRunConfiguration config) throws ConfigurationException {
        config.setGenerateAntlr(this.antlr.isSelected());
        config.setGenerateRRDiagram(this.diagram.isSelected());
        config.setGenerateYacc(this.yacc.isSelected());
        config.setFilePath(this.fileSelector.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        JPanel window = new JPanel();

        window.add(fileSelector);

        window.add(new JLabel("Select which types to generate..."));
        window.add(antlr);
        window.add(diagram);
        window.add(yacc);

        return window;
    }

    /**
     * Returns the state of the button. True if the
     * toggle button is selected, false if it's not.
     * @return true if the toggle button is selected, otherwise false
     */
    public boolean isAntlrSelected() {
        return antlr.isSelected();
    }

    /**
     * Returns the state of the button. True if the
     * toggle button is selected, false if it's not.
     * @return true if the toggle button is selected, otherwise false
     */
    public boolean isYaccSelected() {
        return yacc.isSelected();
    }

    /**
     * Returns the state of the button. True if the
     * toggle button is selected, false if it's not.
     * @return true if the toggle button is selected, otherwise false
     */
    public boolean isRailroadDiagramSelected() {
        return diagram.isSelected();
    }
}
