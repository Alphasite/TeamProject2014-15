package uk.ac.gla.teamL.inspections;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.psi.EBNFAssignment;

/**
 * User: nishad
 * Date: 23/11/14
 * Time: 16:26
 */
public class EBNFLeftRecursionInspection extends LocalInspectionTool implements LocalQuickFix {

    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "Grammar/EBNF";
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Left recursion";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "EBNFLeftRecursionInspection";
    }

    public boolean isEnabledByDefault() {
        return true;
    }

    @Nullable
    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        ProblemDescriptor[] problems = super.checkFile(file, manager, isOnTheFly);

        if (file instanceof EBNFFile) {
            //TODO Implement this method.
        }

        return problems;
    }


    // Quickfix Methods.
    // I'm a bit undecided if this should be a separate class, but w/e.
    // It seems okay atm, in its current state. Lets see how big it gets...

    @NotNull
    @Override
    public String getName() {
        return "Remove Left Recursion";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return this.getName();
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        PsiElement element = problemDescriptor.getPsiElement();

        if (!element.isValid()) {
            return;
        }

        PsiElement parent = element.getParent();

        if (element instanceof EBNFAssignment) {
            //TODO implement this as well.
        }
    }
}
