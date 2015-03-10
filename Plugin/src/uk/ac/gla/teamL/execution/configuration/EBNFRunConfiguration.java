package uk.ac.gla.teamL.execution.configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 12:03
 */
public class EBNFRunConfiguration extends LocatableConfigurationBase implements RefactoringListenerProvider {
    private boolean generateAntlr;
    private boolean generateYacc;
    private boolean generateRRDiagram;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    protected EBNFRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
        this.generateAntlr = false;
        this.generateYacc = false;
        this.generateRRDiagram = false;
    }

    /**
     * Returns the UI control for editing the run configuration settings. If additional control over validation is required, the object
     * returned from this method may also implement {@link com.intellij.execution.impl.CheckableRunConfigurationEditor}. The returned object
     * can also implement {@link com.intellij.openapi.options.SettingsEditorGroup} if the settings it provides need to be displayed in
     * multiple tabs.
     *
     * @return the settings editor component.
     */
    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new EBNFRunConfigurationUI();
    }

    /**
     * Prepares for executing a specific instance of the run configuration.
     *
     * @param executor    the execution mode selected by the user (run, debug, profile etc.)
     * @param environment the environment object containing additional settings for executing the configuration.
     * @return the RunProfileState describing the process which is about to be started, or null if it's impossible to start the process.
     */
    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return null;
    }

    public boolean isGenerateAntlr() {
        return generateAntlr;
    }

    public void setGenerateAntlr(boolean generateAntlr) {
        this.generateAntlr = generateAntlr;
    }

    public boolean isGenerateYacc() {
        return generateYacc;
    }

    public void setGenerateYacc(boolean generateYacc) {
        this.generateYacc = generateYacc;
    }

    public boolean isGenerateRRDiagram() {
        return generateRRDiagram;
    }

    public void setGenerateRRDiagram(boolean generateRRDiagram) {
        this.generateRRDiagram = generateRRDiagram;
    }

    /**
     * Returns a listener to handle a rename or move refactoring of the specified PSI element.
     *
     * @param element the element on which a refactoring was invoked.
     * @return the listener to handle the refactoring, or null if the run configuration doesn't care about refactoring of this element.
     */
    @Nullable
    @Override
    public RefactoringElementListener getRefactoringElementListener(PsiElement element) {
        // TODO implement; see https://searchcode.com/codesearch/view/68848916/
        return null;
//        if (element instanceof EBNFFile) {
//            return null;
//        }
    }
}
