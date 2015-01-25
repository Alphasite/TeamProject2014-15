package uk.ac.gla.teamL.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * User: nishad
 * Date: 18/01/15
 * Time: 11:10
 */
public class EBNFIdentifierReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        return psiElement instanceof EBNFNamedElement ? psiElement.getReferences() : PsiReference.EMPTY_ARRAY;
    }

    @Override
    public boolean acceptsTarget(@NotNull PsiElement target) {
        return super.acceptsTarget(target);
    }
}
