package uk.ac.gla.teamL.editor;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.EBNFReference;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;

import static com.intellij.patterns.PsiJavaPatterns.psiElement;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 22:05
 */
public class EBNFReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(psiElement(EBNFIdentifier.class).withParent(psiElement(EBNFAssignment.class)), new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
                if (psiElement.getText() != null) {
                    return new PsiReference[]{EBNF};
                }
                return new PsiReference[0];            }
        });

        psiReferenceRegistrar.registerReferenceProvider(psiElement(BnfStringImpl.class).withParent(psiElement(BnfAttrPattern.class)), new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                return new PsiReference[]{BnfStringImpl.createPatternReference((BnfStringImpl)element)};
            }
        });
    }
}
