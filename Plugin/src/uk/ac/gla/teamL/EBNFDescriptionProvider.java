package uk.ac.gla.teamL;

import com.intellij.psi.*;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 23:57
 */
public class EBNFDescriptionProvider implements ElementDescriptionProvider {
    @Nullable
    @Override
    public String getElementDescription(PsiElement psiElement, ElementDescriptionLocation elementDescriptionLocation) {
        if (psiElement instanceof PsiNamedElement) {
            boolean isAssignment = psiElement.getParent() instanceof PsiNameIdentifierOwner;
            return /*(isAssignment? "Rule: " : "Identifier: ") +*/ ((EBNFIdentifier) psiElement).getName();
        } else {
            return null;
        }
    }
}
