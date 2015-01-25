package uk.ac.gla.teamL.psi.impl;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.EBNFNamedElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: nishad
 * Date: 06/12/14
 * Time: 19:34
 */
public class EBNFIdentifierReferenceImpl<T extends EBNFNamedElement> extends PsiReferenceBase<T> {

    public EBNFIdentifierReferenceImpl(T element, TextRange range) {
        super(element, range);
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        String elementName = ((EBNFNamedElement) element).getName();
        String myElementName = myElement.getName();

        return element.getContainingFile().equals(element.getContainingFile())
            && myElementName != null && myElementName.equals(elementName);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        // Shouldn't be able to reference your self.
        if (myElement != null && !(myElement.getParent() instanceof EBNFAssignment)) {
            PsiFile containingFile = myElement.getContainingFile();
            String referenceName = getRangeInElement().substring(myElement.getText());

            // Find the matching rule.
            for (EBNFAssignment assignment : EBNFParserUtil.findRules(containingFile)) {
                if (assignment != null && assignment.getName().equals(referenceName)) {
                    return assignment;
                }
            }
        }

        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Set<String> variantsSet = new HashSet<>();
        List<LookupElement> variants = new ArrayList<>();

        // I could user get rules, but i thought it might be helpful to provide undefined nodes as completions as well.
        for (EBNFIdentifier identifier:  EBNFParserUtil.findIdentifiers(myElement.getContainingFile())) {
            if (!variantsSet.contains(identifier.getName())) {
                variants.add(LookupElementBuilder.create(identifier));
                variantsSet.add(identifier.getName());
            }
        }

        return variants.toArray();
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        myElement.setName(newElementName);
        return myElement;
    }
}
