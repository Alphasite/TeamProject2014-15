package uk.ac.gla.teamL.execution.configuration;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.util.Ref;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 13:08
 */
public class EBNFRunConfigurationProducer extends RunConfigurationProducer {
    protected EBNFRunConfigurationProducer() {
        super(new EBNFConfigurationFactory());
    }

    /**
     * Sets up a configuration based on the specified context.
     *
     * @param configuration a clone of the template run configuration of the specified type
     * @param context       contains the information about a location in the source code.
     * @param sourceElement a reference to the source element for the run configuration (by default contains the element at caret,
     *                      can be updated by the producer to point to a higher-level element in the tree).
     * @return true if the context is applicable to this run configuration producer, false if the context is not applicable and the
     * configuration should be discarded.
     */
    @Override
    protected boolean setupConfigurationFromContext(RunConfiguration configuration, ConfigurationContext context, Ref sourceElement) {
        if (configuration instanceof EBNFRunConfiguration) {
            Location filePath = context.getLocation();
            if (filePath != null) {
                ((EBNFRunConfiguration) configuration).setFilePath(filePath.toString());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Checks if the specified configuration was created from the specified context.
     *
     * @param configuration a configuration instance.
     * @param context       contains the information about a location in the source code.
     * @return true if this configuration was created from the specified context, false otherwise.
     */
    @Override
    public boolean isConfigurationFromContext(RunConfiguration configuration, ConfigurationContext context) {
        if (configuration instanceof EBNFRunConfiguration) {
            Location filePath = context.getLocation();
            if (filePath != null) {
                return ((EBNFRunConfiguration) configuration).getFilePath().equals(filePath.toString());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
