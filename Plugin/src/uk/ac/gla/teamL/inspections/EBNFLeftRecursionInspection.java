package uk.ac.gla.teamL.inspections;

import com.intellij.codeInspection.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFFile;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.EBNFRuleElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        ProblemsHolder problemsHolder = new ProblemsHolder(manager, file, isOnTheFly);

        if (file instanceof EBNFFile) {
            //TODO Implement this method.
            for (EBNFAssignment ebnfAssignment : EBNFParserUtil.findRules(file)) {
                if (isLeftRecursive(ebnfAssignment)) {
                    problemsHolder.registerProblem(ebnfAssignment, "Left Recursive: Some parser generators don't like this.");
                }
            }
        }

        return problems;
    }

    private static boolean isLeftRecursive(EBNFAssignment assignment) {

        ArrayList<String> visited = new ArrayList<String>();

        String name = assignment.getName();

        Stack<EBNFAssignment> stack = new Stack<EBNFAssignment>();
        stack.push(assignment);
        visited.add(assignment.getName());

        while (!stack.empty()) {
            EBNFAssignment poppedAssignment = stack.pop();
            ArrayList<String> identifiers = getIdentifiers(poppedAssignment);

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

    private static ArrayList<String> getIdentifiers(EBNFAssignment assignment) {
        ArrayList<String> identifiers = new ArrayList<String>();

        List<EBNFRuleElement> rules = assignment.getRules().getRuleElementList();
        for (EBNFRuleElement rule : rules) {
            PsiElement child = rule.getFirstChild();
            if (child instanceof EBNFIdentifier) {
                PsiElement prevSibling = rule.getPrevSibling();

                if (prevSibling != null) {
                    if (prevSibling instanceof PsiWhiteSpace) {
                        prevSibling = prevSibling.getPrevSibling();
                    }
                    String token = prevSibling.getText();
                    if (token.equals("|") || token.equals("=")) {
                        identifiers.add(rule.getText());
                    }
                }
            }
        }
        return identifiers;
    }

    private static EBNFAssignment getAssignment(String identifier, EBNFAssignment assignment) {
        PsiElement psiElement = assignment.getPrevSibling();
        while (psiElement != null) {
            if (psiElement instanceof EBNFAssignment) {
                if ( ((EBNFAssignment) psiElement).getName().equals(identifier) ) {
                    return (EBNFAssignment) psiElement;
                }
            }
            psiElement = psiElement.getPrevSibling();
        }

        psiElement = assignment.getNextSibling();
        while (psiElement != null) {
            if (psiElement instanceof EBNFAssignment) {
                if ( ((EBNFAssignment) psiElement).getName().equals(identifier) ) {
                    return (EBNFAssignment) psiElement;
                }
            }
            psiElement = psiElement.getNextSibling();
        }
        return null;
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
