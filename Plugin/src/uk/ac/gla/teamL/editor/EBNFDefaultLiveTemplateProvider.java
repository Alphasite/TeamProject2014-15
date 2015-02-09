package uk.ac.gla.teamL.editor;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 28/01/15
 * Time: 10:36
 */
public class EBNFDefaultLiveTemplateProvider implements DefaultLiveTemplatesProvider {
    @Override
    public String[] getDefaultLiveTemplateFiles() {
        return new String[]{"templates/EBNFLiveTemplate"};
    }

    /**
     * @return paths to resources, without .xml extension (e.g. /templates/foo)
     */
    @Nullable
    @Override
    public String[] getHiddenLiveTemplateFiles() {
        return new String[0];
    }
}
