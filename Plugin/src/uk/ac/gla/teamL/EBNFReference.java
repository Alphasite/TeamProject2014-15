package uk.ac.gla.teamL;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 25/01/15
 * Time: 21:13
 */
public class EBNFReference extends PsiReferenceBase<EBNFIdentifier> {
    private String name;

    public EBNFReference(@NotNull PsiElement element, TextRange textRange) {
        super((EBNFIdentifier) element, textRange);
        name = ((EBNFIdentifier) element).getName();
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
            if (rule.getName().length() > 0) {
                variants.add(LookupElementBuilder.create(rule));
            }
        }

        return variants.toArray();
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return ((EBNFIdentifier) myElement).setName(newElementName);
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
