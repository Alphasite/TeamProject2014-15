package uk.ac.gla.teamL;

import com.intellij.lang.Language;

/**
 * User: nishad
 * Date: 27/10/14
 * Time: 15:43
 */
public class EBNFLanguage extends Language {
    public static final EBNFLanguage INSTANCE = new EBNFLanguage();

    private EBNFLanguage() {
        super("EBNF");
    }

    @Override
    public String getDisplayName() {
        return "EBNF";
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }
}
