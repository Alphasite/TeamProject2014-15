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
public class EBNFReference extends PsiReferenceBase<PsiNamedElement>implements PsiPolyVariantReference {
    private String name;

    public EBNFReference(@NotNull PsiElement element, TextRange textRange) {
        super((PsiNamedElement) element, textRange);
        name = ((PsiNamedElement) element).getName();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        final List<EBNFAssignment> rules = EBNFParserUtil.findRules(myElement.getContainingFile(), name);

        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (EBNFAssignment property : rules) {
            results.add(new PsiElementResolveResult(property.getId()));
        }

        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
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
