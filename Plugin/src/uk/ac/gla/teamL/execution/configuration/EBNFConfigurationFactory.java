package uk.ac.gla.teamL.execution.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 12:07
 */
public class EBNFConfigurationFactory extends ConfigurationFactory {
    protected EBNFConfigurationFactory() {
        super(new EBNFConfigurationType());
    }

    /**
     * Creates a new template run configuration within the context of the specified project.
     *
     * @param project the project in which the run configuration will be used
     * @return the run configuration instance.
     */
    @Override
    public RunConfiguration createTemplateConfiguration(Project project) {
        return new EBNFRunConfiguration(project, new EBNFConfigurationFactory(), "Unnamed");
    }
}
