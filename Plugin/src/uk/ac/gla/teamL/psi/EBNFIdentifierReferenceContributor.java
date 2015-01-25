package uk.ac.gla.teamL.psi;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

/**
 * User: nishad
 * Date: 28/12/14
 * Time: 19:18
 */
public class EBNFIdentifierReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiNamedElement.class), new EBNFIdentifierReferenceProvider());
    }
}
