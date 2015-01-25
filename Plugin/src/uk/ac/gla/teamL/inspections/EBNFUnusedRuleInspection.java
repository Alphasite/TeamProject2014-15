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
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

import java.util.HashSet;
import java.util.Set;

/**
 * User: nishad
 * Date: 23/01/15
 * Time: 20:47
 */
public class EBNFUnusedRuleInspection extends LocalInspectionTool {
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "Grammar/EBNF";
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Unused Rule";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "EBNFUnusedRuleInspection";
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
            Set<String> usedIdentifiers = new HashSet<>();

            if (rules != null) {
                for (EBNFAssignment rule: rules) {
                    for (EBNFIdentifier identifier : EBNFParserUtil.findIdentifiers(rule.getRules())) {
                        usedIdentifiers.add(identifier.getName().toLowerCase());
                    }
                }

                for (EBNFAssignment rule: rules) {
                    if (!usedIdentifiers.contains(rule.getName().toLowerCase())) {
                        problemsHolder.registerProblem(rule.getId(), "Rule is never used.");
                    }
                }
            }
        }

        return problemsHolder.getResultsArray();
    }
}
