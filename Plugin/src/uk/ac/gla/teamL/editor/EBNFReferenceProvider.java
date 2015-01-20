package uk.ac.gla.teamL.editor;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.psi.EBNFNamedElement;

/**
 * User: nishad
 * Date: 18/01/15
 * Time: 11:10
 */
public class EBNFReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        return psiElement instanceof EBNFNamedElement ? psiElement.getReferences() : PsiReference.EMPTY_ARRAY;
    }
}
