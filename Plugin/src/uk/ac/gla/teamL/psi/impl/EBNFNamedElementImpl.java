package uk.ac.gla.teamL.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFReference;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFNamedElement;

/**
 * User: nishad
 * Date: 25/01/15
 * Time: 21:00
 */
public abstract class EBNFNamedElementImpl extends ASTWrapperPsiElement implements EBNFNamedElement {
    public EBNFNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return super.getReferences();
    }

    @Override
    public PsiReference getReference() {
        if (!(this.getParent() instanceof EBNFAssignment)) {
            return new EBNFReference(this, this.getTextRange());
        } else {
            return super.getReference();
        }
    }


}
