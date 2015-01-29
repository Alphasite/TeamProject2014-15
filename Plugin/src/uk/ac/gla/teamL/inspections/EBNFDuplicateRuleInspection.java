package uk.ac.gla.teamL.inspections;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.psi.EBNFAssignment;

import java.util.HashMap;
import java.util.Map;

/**
 * User: nishad
 * Date: 24/01/15
 * Time: 14:15
 */
public class EBNFDuplicateRuleInspection extends LocalInspectionTool {
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "Grammar/EBNF";
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Duplicate Rule Name";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "EBNFDuplicateRuleInspection";
    }

    public boolean isEnabledByDefault() {
        return true;
    }

    @Nullable
    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        super.checkFile(file, manager, isOnTheFly);

        ProblemsHolder problemsHolder = new ProblemsHolder(manager, file, isOnTheFly);

        if (file instanceof EBNFFile) {
            EBNFAssignment[] rules = PsiTreeUtil.getChildrenOfType(file, EBNFAssignment.class);

            Map<String, Integer> declaredIdentifiers = new HashMap<>();

            if (rules != null) {
                for (EBNFAssignment rule: rules) {
                    String name = rule.getName().toLowerCase();
                    if (declaredIdentifiers.containsKey(name)) {
                        declaredIdentifiers.put(name, declaredIdentifiers.get(name) + 1);
                    } else {
                        declaredIdentifiers.put(name, 1);
                    }
                }

                for (EBNFAssignment rule: rules) {
                    if (declaredIdentifiers.get(rule.getName().toLowerCase()) > 1) {
                        problemsHolder.registerProblem(rule.getId(), "Rule has multiple declarations.");
                    }
                }
            }
        }

        return problemsHolder.getResultsArray();
    }
}
