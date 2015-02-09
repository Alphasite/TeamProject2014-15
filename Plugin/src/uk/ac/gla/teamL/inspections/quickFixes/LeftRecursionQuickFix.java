package uk.ac.gla.teamL.inspections.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.psi.*;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.gla.teamL.parser.EBNFParserUtil.findRules;

/**
 * Created by Bryan on 27/01/15.
 */
public class LeftRecursionQuickFix implements LocalQuickFix {
    // Quickfix Methods.

    PsiFile file;

    public LeftRecursionQuickFix(PsiFile file) {
        this.file = file;
    }

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

        List<EBNFAssignment> assignments = findRules(file);


        for (int i=0; i < assignments.size(); i++) {
            for (int j=0; j < i; j++) {
                replaceProductions(assignments.get(i), assignments.get(j).getName());
            }
            fixDirectLeftRecursion(assignments.get(i), file);
        }

    }

    private static void fixDirectLeftRecursion(EBNFAssignment ebnfAssignment, PsiFile file) {
        EBNFAssignment assignment = getAssignment(ebnfAssignment.getName(), file);

        String name = assignment.getName();
        String primedName = name + "_";

        EBNFRules rules = assignment.getRules();
        List<EBNFRulesSegment> productions = rules.getRulesSegmentList(); //EBNFParserImplUtil.getRuleSegmentList(rules);

        ArrayList<List<EBNFRuleElement>> recursiveProductions = new ArrayList<>();
        ArrayList<List<EBNFRuleElement>> nonRecursiveProductions = new ArrayList<>();

        for (EBNFRulesSegment production: productions) {
            if (isDirectLeftRecursive(name, production.getRuleElementList())) {
                recursiveProductions.add(production.getRuleElementList());
            } else {
                nonRecursiveProductions.add(production.getRuleElementList());
            }
        }
        if (recursiveProductions.isEmpty()) {
            return;
        }

        StringBuilder newAssignmentBuilder = new StringBuilder();
        newAssignmentBuilder.append("let " + name + " =");

        StringBuilder primedAssignmentBuilder = new StringBuilder();
        primedAssignmentBuilder.append("let " + primedName + " =");

        for (int i = 0; i < nonRecursiveProductions.size(); i ++) {
            List<EBNFRuleElement> nonRecursiveProduction = nonRecursiveProductions.get(i);
            StringBuilder productionBuilder = new StringBuilder();

            for (EBNFRuleElement element: nonRecursiveProduction) {
                productionBuilder.append(" " + element.getText());
            }

            productionBuilder.append(" " + primedName);

            newAssignmentBuilder.append(productionBuilder.toString());

            if (i < nonRecursiveProductions.size()-1) {
                newAssignmentBuilder.append(" |");
            }
        }
        newAssignmentBuilder.append(";");

        for (int i = 0; i < recursiveProductions.size(); i++) {
            List<EBNFRuleElement> recursiveProduction = recursiveProductions.get(i);
            StringBuilder productionBuilder = new StringBuilder();

            StringBuilder betaBuilder = new StringBuilder();
            for (int j = 1; j < recursiveProduction.size(); j++) {
                betaBuilder.append(" " + recursiveProduction.get(j).getText());
            }

            productionBuilder.append(" (" + betaBuilder.toString() + " " + primedName + " )?");

            primedAssignmentBuilder.append(productionBuilder.toString());

            if (i < recursiveProductions.size()-1) {
                primedAssignmentBuilder.append(" |");
            }
        }
        primedAssignmentBuilder.append(";");

        EBNFAssignment newAssignment = createNewAssignment((PsiElement) assignment, newAssignmentBuilder.toString());
        EBNFAssignment primedAssignment = createNewAssignment((PsiElement) assignment, primedAssignmentBuilder.toString());

        assignment.getParent().addAfter((PsiElement) primedAssignment, (PsiElement) assignment);
        assignment.replace((PsiElement) newAssignment);

    }

    private static boolean isDirectLeftRecursive(String name, List<EBNFRuleElement> production) {
        return production.get(0).getText().equals(name);
    }

    private static void replaceProductions(EBNFAssignment assignment, String identifier) {
        StringBuilder rAssignmentBuilder = new StringBuilder();
        rAssignmentBuilder.append("let " + assignment.getName() + " =");

        EBNFRules rules = assignment.getRules();
        List<EBNFRulesSegment> productions = rules.getRulesSegmentList();

        for (int i = 0; i < productions.size(); i++) {
            List<EBNFRuleElement> production = productions.get(i).getRuleElementList();
            if ((production.get(0).getFirstChild() instanceof EBNFIdentifier) && (production.get(0).getText().equals(identifier))) {
                String newProductions = replaceLeftmostIdentifier(production, identifier);
                rAssignmentBuilder.append(" " + newProductions);
            } else {
                for (EBNFRuleElement ruleElement: production) {
                    rAssignmentBuilder.append(" " + ruleElement.getText());
                }
            }
            if (i == productions.size()-1) {
                rAssignmentBuilder.append(";");
            } else {
                rAssignmentBuilder.append("|");
            }
        }
        EBNFAssignment rAssignment = createNewAssignment((PsiElement)assignment, rAssignmentBuilder.toString());
        assignment.replace((PsiElement) rAssignment);
        commitChanges(assignment);

    }

    private static void commitChanges(PsiElement element) {
        // Commit changes to file to PSI tree; does NOT write to disk
        Project project = element.getProject();
        PsiDocumentManager docMgr = PsiDocumentManager.getInstance(project);
        docMgr.commitAllDocuments();
    }

    private static String replaceLeftmostIdentifier(List<EBNFRuleElement> production, String identifier) {
        StringBuilder newProduction = new StringBuilder();

        StringBuilder betaBuilder = new StringBuilder();
        for (int i = 1; i < production.size(); i++) {
            betaBuilder.append(production.get(i).getText() + " ");
        }

        PsiFile file = production.get(0).getContainingFile();
        EBNFAssignment assignment = getAssignment(identifier, file);
        EBNFRules rules = assignment.getRules();
        List<EBNFRulesSegment> productions = rules.getRulesSegmentList();
        for (int i = 0; i < productions.size(); i++){
            List<EBNFRuleElement> rProduction = productions.get(i).getRuleElementList();
            StringBuilder alphaBuilder = new StringBuilder();
            for (EBNFRuleElement element: rProduction) {
                alphaBuilder.append(element.getText() + " ");
            }
            newProduction.append(alphaBuilder.toString() + betaBuilder.toString());
            if (i < productions.size()-1) {
                newProduction.append("| ");
            }
        }

        return newProduction.toString();
    }

    private static EBNFAssignment createNewAssignment(PsiElement element, String assignment) {
        if (element == null) {return null;}
        PsiFile file = EBNFElementFactory.createFile(element.getProject(), assignment);
        return (EBNFAssignment)file.getFirstChild();
    }

    private static EBNFAssignment getAssignment(String identifier, PsiFile file) {
        List<EBNFAssignment> rules = findRules(file, identifier);
        if (rules.size() > 0) {
            return rules.get(0);
        } else {
            return null;
        }
    }

}
