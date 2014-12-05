package uk.ac.gla.teamL.parser;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.parser.psi.impl.EBNFAssignmentImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 21:07
 */
public class EBNFReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    String name;

    public EBNFReference(PsiElement element, TextRange range) {
        super(element, range);
        this.name = element.getText().substring(range.getStartOffset(), range.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b) {

        List<ResolveResult> references = new ArrayList<>();

        for (EBNFAssignmentImpl assignment: EBNFParserUtil.findRules(myElement.getContainingFile())) {
            references.add(new PsiElementResolveResult(assignment));
        }

        return references.toArray(new ResolveResult[references.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] results = multiResolve(false);

        if (results.length == 0) {
            return results[0].getElement();
        } else {
            return null;
        }
    }

    @NotNull
    @Override
    public Object[] getVariants() {

        PsiFile file = myElement.getContainingFile();
        List<EBNFAssignmentImpl> rules = EBNFParserUtil.findRules(file);
        List<LookupElement> variants = new ArrayList<LookupElement>();

        for (EBNFAssignmentImpl rule: rules) {
            if (rule != null) {

                String name = rule.getId().getName();
                if (name != null) {
                    variants.add(LookupElementBuilder.create(rule));
                }
            }
        }

        return variants.toArray();
    }
}
