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
import uk.ac.gla.teamL.psi.EBNFNamedElement;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 06/12/14
 * Time: 19:34
 */
public class EBNFIdentifierReferenceImpl<T extends EBNFNamedElement> extends PsiReferenceBase<T> {

    public EBNFIdentifierReferenceImpl(@NotNull T element) {
        super(element);
    }

    public EBNFIdentifierReferenceImpl(T element, TextRange range) {
        super(element, range);
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        String elementName = ((EBNFNamedElement) element).getName();
        String myElementName = myElement.getName();

        return myElementName != null && elementName != null                     // Exists
            && myElementName.toLowerCase().equals(elementName.toLowerCase())    // Has the same name
            && !(myElement.getParent() instanceof EBNFAssignment);              // And isn't its self.
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        // Shouldn't be able to reference your self.
        if (myElement != null /* TODO Check and remove: */ && !(myElement.getParent() instanceof EBNFAssignment)) {
            PsiFile containingFile = myElement.getContainingFile();
            String referenceName = myElement.getName();

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
        List<LookupElement> variants = new ArrayList<>();

        for (EBNFAssignment identifier:  EBNFParserUtil.findRules(myElement.getContainingFile())) {
            variants.add(LookupElementBuilder.create(identifier.getId()));
        }

        return variants.toArray(new LookupElement[variants.size()]);
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        myElement.setName(newElementName);
        return myElement;
    }


}
