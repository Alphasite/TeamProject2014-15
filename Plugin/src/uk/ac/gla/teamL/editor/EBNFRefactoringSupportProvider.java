package uk.ac.gla.teamL.editor;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

/**
 * User: nishad
 * Date: 05/12/14
 * Time: 22:41
 */
public class EBNFRefactoringSupportProvider extends RefactoringSupportProvider {
    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement element, PsiElement context) {
        return element instanceof EBNFIdentifier;
    }
}
