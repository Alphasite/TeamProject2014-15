package uk.ac.gla.teamL;

import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 22/12/14
 * Time: 16:43
 */
public class EBNFScopeProcessor implements PsiScopeProcessor {
    @Override
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        return false;
    }

    @Nullable
    @Override
    public <T> T getHint(Key<T> key) {
        return null;
    }

    @Override
    public void handleEvent(Event event, @Nullable Object o) {

    }
}
