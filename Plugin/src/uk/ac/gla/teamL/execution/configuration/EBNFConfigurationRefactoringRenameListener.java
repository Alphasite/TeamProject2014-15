package uk.ac.gla.teamL.execution.configuration;

import com.intellij.execution.configurations.RefactoringListenerProvider;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 13:01
 */
public class EBNFConfigurationRefactoringRenameListener implements RefactoringListenerProvider {
    /**
     * Returns a listener to handle a rename or move refactoring of the specified PSI element.
     *
     * @param element the element on which a refactoring was invoked.
     * @return the listener to handle the refactoring, or null if the run configuration doesn't care about refactoring of this element.
     */
    @Nullable
    @Override
    public RefactoringElementListener getRefactoringElementListener(PsiElement element) {
        return null;
    }
}
