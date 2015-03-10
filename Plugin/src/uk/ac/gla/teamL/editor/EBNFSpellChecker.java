package uk.ac.gla.teamL.editor;


import com.intellij.psi.PsiElement;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 13:36
 */
public class EBNFSpellChecker extends SpellcheckingStrategy {
    @NotNull
    @Override
    public Tokenizer getTokenizer(PsiElement element) {
        return super.getTokenizer(element);

    }
}
