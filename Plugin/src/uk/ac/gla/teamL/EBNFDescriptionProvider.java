package uk.ac.gla.teamL;

import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.ElementDescriptionProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 23:57
 */
public class EBNFDescriptionProvider implements ElementDescriptionProvider {
    @Nullable
    @Override
    public String getElementDescription(@NotNull PsiElement psiElement, @NotNull ElementDescriptionLocation elementDescriptionLocation) {
        if (psiElement instanceof PsiNamedElement) {
            return ((PsiNamedElement) psiElement).getName();
        } else {
            return null;
        }
    }
}
