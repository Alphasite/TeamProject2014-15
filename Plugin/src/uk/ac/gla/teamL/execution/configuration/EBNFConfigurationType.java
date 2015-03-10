package uk.ac.gla.teamL.execution.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFIcon;

import javax.swing.*;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 12:05
 */
public class EBNFConfigurationType implements ConfigurationType {
    /**
     * Returns the display name of the configuration type. This is used, for example, to represent the configuration type in the run
     * configurations tree, and also as the name of the action used to create the configuration.
     *
     * @return the display name of the configuration type.
     */
    @Override
    public String getDisplayName() {
        return "eBNF Grammar Generator";
    }

    /**
     * Returns the description of the configuration type. You may return the same text as the display name of the configuration type.
     *
     * @return the description of the configuration type.
     */
    @Override
    public String getConfigurationTypeDescription() {
        return "Generate the specified grammars from the eBNF file.";
    }

    /**
     * Returns the 16x16 icon used to represent the configuration type.
     *
     * @return the icon
     */
    @Override
    public Icon getIcon() {
        return EBNFIcon.FILE;
    }

    /**
     * Returns the ID of the configuration type. The ID is used to store run configuration settings in a project or workspace file and
     * must not change between plugin versions.
     *
     * @return the configuration type ID.
     */
    @NotNull
    @Override
    public String getId() {
        return "EBNFGenerateConfig";
    }

    /**
     * Returns the configuration factories used by this configuration type. Normally each configuration type provides just a single factory.
     * You can return multiple factories if your configurations can be created in multiple variants (for example, local and remote for an
     * application server).
     *
     * @return the run configuration factories.
     */
    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[] {new EBNFConfigurationFactory()};
    }
}
