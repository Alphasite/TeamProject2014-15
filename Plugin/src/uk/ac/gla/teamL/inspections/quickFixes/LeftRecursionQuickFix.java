package uk.ac.gla.teamL.inspections.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.EBNFParserUtil;
import uk.ac.gla.teamL.psi.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by Bryan on 27/01/15.
 */
public class LeftRecursionQuickFix implements LocalQuickFix {
    // Quickfix Methods.

    @NotNull
    @Override
    public String getName() {
        return "Refactor Left Recursion";
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

        List<EBNFAssignment> assignments = EBNFParserUtil.findRules(element.getContainingFile());

        for (EBNFAssignment assignment : assignments) {
            System.out.println("->" + assignment.getName());
        }

        for (int i=0; i < assignments.size(); i++) {
            System.out.println(assignments.get(i).getName());

            for (int j=0; j < i; j++) {
                System.out.println("i = " + i + ", j = " + j);

                Collection<EBNFRules> rules =  PsiTreeUtil.findChildrenOfType(assignments.get(i), EBNFRules.class);
                
                replaceProductions(assignments.get(i), assignments.get(j).getName());
                //assignments.add(i);
                System.out.println("-->" + rules.size());
            }
            System.out.println("Fix dir-LR.");
            //eliminateImediateLeftRecursion();
        }

//            element.addAfter((EBNFAssignment)file1.getFirstChild(), element);
//            EBNFAssignment child = (EBNFAssignment)file.getFirstChild();
//            element.addAfter(child,element);
//            element.delete();
//        }
    }

    private static void replaceProductions(EBNFAssignment assignment, String productionReplacement) {
        /*
        for each rule
            if leftmost == identifier
                injectAssignment()
         */

        EBNFRules rules = assignment.getRules();
        PsiElement element = rules.getFirstChild();

        for (EBNFRuleElement rule : rules.) {
            System.out.println("!--->" + rule.getText());
        }

    }

    private static EBNFAssignment createNewAssignment(PsiElement element, String assignment) {
        if (element == null) {return null;}
        PsiFile file = EBNFElementFactory.createFile(element.getProject(), assignment);
        return (EBNFAssignment)file.getFirstChild();
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

    private static boolean isFirstRuleIdentifier(EBNFAssignment assignment) {
        return false;
    }
}
