package uk.ac.gla.teamL.inspections;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.inspections.quickFixes.EBNFLeftRecursionQuickFix;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.EBNFOr;
import uk.ac.gla.teamL.psi.EBNFRulesSegment;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class EBNFLeftRecursionInspection extends LocalInspectionTool {

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
        ProblemsHolder problemsHolder = new ProblemsHolder(manager, file, isOnTheFly);

        if (file instanceof EBNFFile) {
            //TODO Implement this method.
            for (EBNFAssignment ebnfAssignment : EBNFParserUtil.findRules(file)) {
                if (isLeftRecursive(ebnfAssignment)) {
                    problemsHolder.registerProblem(
                            ebnfAssignment,
                            "Left Recursive: Some parser generators don't like this.",
                            new EBNFLeftRecursionQuickFix(ebnfAssignment.getContainingFile())
                    );
                }
            }
        }

        return problemsHolder.getResultsArray();
    }

    private static boolean isLeftRecursive(EBNFAssignment assignment) {

        ArrayList<String> visited = new ArrayList<String>();

        String name = assignment.getName();

        Stack<EBNFAssignment> stack = new Stack<EBNFAssignment>();
        stack.push(assignment);
        visited.add(assignment.getName());

        while (!stack.empty()) {
            EBNFAssignment poppedAssignment = stack.pop();
            List<String> identifiers = getLeftmostIdentifiers(poppedAssignment);

            for (String identifier: identifiers) {
                if (identifier.equals(name)) {
                    stack.clear();
                    return true;
                } else {
                    EBNFAssignment idAssignment = getAssignment(identifier, poppedAssignment);
                    if (idAssignment != null && !(visited.contains(idAssignment.getName()))) {
                        stack.push(idAssignment);
                        visited.add(idAssignment.getName());
                    }
                }
            }
        }
        return false;
    }

    private static List<String> getLeftmostIdentifiers(EBNFAssignment assignment) {
        List<String> identifiers = new ArrayList<String>();

        List<EBNFRulesSegment> rulesSegments = PsiTreeUtil.getChildrenOfTypeAsList(
            assignment.getRules(),
            EBNFRulesSegment.class
        );

        for (EBNFRulesSegment rule : rulesSegments) {

            if (rule.getFirstChild() instanceof EBNFIdentifier) {

                PsiElement prevSibling = rule.getPrevSibling();

                if (prevSibling == null) {
                    identifiers.add(rule.getText());
                    continue;
                }

                if (prevSibling instanceof PsiWhiteSpace) {
                    prevSibling = prevSibling.getPrevSibling();
                }

                if (prevSibling instanceof EBNFOr) {
                    identifiers.add(rule.getText());
                }
            }
        }

        return identifiers;
    }

    private static EBNFAssignment getAssignment(String identifier, EBNFAssignment assignment) {
        PsiFile file = assignment.getContainingFile();
        List<EBNFAssignment> rules = EBNFParserUtil.findRules(file, identifier);

        if (rules.size() > 0) {
            return rules.get(0);
        } else {
            return null;
        }
    }
}
