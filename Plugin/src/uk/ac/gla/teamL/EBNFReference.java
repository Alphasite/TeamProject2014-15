package uk.ac.gla.teamL;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 25/01/15
 * Time: 21:13
 */
public class EBNFReference extends PsiReferenceBase<PsiNamedElement> {
    private String name;

    public EBNFReference(@NotNull PsiElement element, TextRange textRange) {
        super((PsiNamedElement) element, textRange);
        name = ((PsiNamedElement) element).getName();
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        List<EBNFAssignment> rules = EBNFParserUtil.findRules(myElement.getContainingFile(), name);
        return rules.size() >= 1? rules.get(0) : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        List<EBNFAssignment> properties = EBNFParserUtil.findRules(myElement.getContainingFile());
        List<LookupElement> variants = new ArrayList<LookupElement>();

        for (EBNFAssignment rule : properties) {
            if (rule.getName() != null && rule.getName().length() > 0) {
                variants.add(LookupElementBuilder.create(rule.getId()));
            }
        }

        return variants.toArray();
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
