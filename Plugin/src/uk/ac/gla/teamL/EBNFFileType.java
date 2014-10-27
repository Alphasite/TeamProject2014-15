package uk.ac.gla.teamL;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: nishad
 * Date: 27/10/14
 * Time: 15:25
 */
public class EBNFFileType extends LanguageFileType {
    public static final EBNFFileType INSTANCE = new EBNFFileType(EBNFLanguage.INSTANCE);

    protected EBNFFileType(@NotNull Language language) {
        super(language);
    }

    @NotNull
    @Override
    public String getName() {
        return "EBNF";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "An EBNF file which can be compiled into a number of parser generator formats.";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "ebnf";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return EBNFIcon.FILE;
    }

}
