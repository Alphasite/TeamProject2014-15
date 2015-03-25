package uk.ac.gla.teamL.inspections.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFUtil;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

/**
 * User: nishad
 * Date: 24/03/15
 * Time: 22:16
 */
public class EBNFUndefinedRuleQuickFix implements LocalQuickFix {
    /**
     * Returns the name of the quick fix.
     *
     * @return the name of the quick fix.
     */
    @NotNull
    @Override
    public String getName() {
        return "Define Rule";
    }

    /**
     * @return text to appear in "Apply Fix" popup when multiple Quick Fixes exist (in the results of batch code inspection). For example,
     * if the name of the quickfix is "Create template &lt;filename&gt", the return value of getFamilyName() should be "Create template".
     * If the name of the quickfix does not depend on a specific element, simply return getName().
     */
    @NotNull
    @Override
    public String getFamilyName() {
        return this.getName();
    }

    /**
     * Called to apply the fix.
     *
     * @param project    {@link com.intellij.openapi.project.Project}
     * @param descriptor problem reported by the tool which provided this quick fix action
     */
    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        PsiElement element = descriptor.getPsiElement();

        if (element instanceof EBNFIdentifier) {
            PsiElement[] rule = EBNFUtil.createElementAndGetAllChildren(project, "\n let " + ((EBNFIdentifier) element).getName() + " =  ;");
            PsiFile file = element.getContainingFile().getOriginalFile();

            for (PsiElement thisRule : rule) {
                file.addAfter(thisRule, file.getLastChild());
            }

            commitChanges(file);
        }
    }

    private static void commitChanges(PsiElement element) {
        // This may be referenced from the official doc, but im not 100%
        // Commit changes to file to PSI tree; does NOT write to disk
        Project project = element.getProject();
        PsiDocumentManager docMgr = PsiDocumentManager.getInstance(project);
        docMgr.commitAllDocuments();
    }
}
