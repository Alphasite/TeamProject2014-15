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
import uk.ac.gla.teamL.inspections.quickFixes.EBNFUndefinedRuleQuickFix;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: nishad
 * Date: 24/01/15
 * Time: 14:07
 */
public class EBNFUndeclaredRuleInspection extends LocalInspectionTool {
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "Grammar/EBNF";
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Undeclared Identifier";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "EBNFUndeclaredIdentifierInspection";
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
            List<EBNFIdentifier> identifiers = EBNFParserUtil.findIdentifiers(file);

            Set<String> declaredIdentifiers = new HashSet<>();

            if (rules != null && rules.length > 0) {
                for (EBNFAssignment rule: rules) {
                    declaredIdentifiers.add(rule.getName().toLowerCase());
                }

                for (EBNFIdentifier identifier: identifiers) {
                    if (!declaredIdentifiers.contains(identifier.getName().toLowerCase())) {
                        problemsHolder.registerProblem(identifier, "Identifier doesnt point to a known rule.", new EBNFUndefinedRuleQuickFix());
                    }
                }
            }
        }

        return problemsHolder.getResultsArray();
    }
}
