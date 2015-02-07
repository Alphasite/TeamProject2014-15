package uk.ac.gla.teamL.editor;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.psi.EBNFAssignment;
import uk.ac.gla.teamL.psi.EBNFIdentifier;
import uk.ac.gla.teamL.psi.EBNFRuleElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EBNFLeftRecursionAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof EBNFAssignment) {
            EBNFAssignment assignment = (EBNFAssignment) psiElement;

            if (isLeftRecursive(assignment)) {
                annotationHolder.createWeakWarningAnnotation(assignment, "Rule is left recursive: May cause issues with certain parser generators.");
            }
        }
    }

    private static boolean isLeftRecursive(EBNFAssignment assignment) {

        ArrayList<String> visited = new ArrayList<>();

        String name = assignment.getName();

        Stack<EBNFAssignment> stack = new Stack<>();
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
        ArrayList<String> identifiers = new ArrayList<>();

            List<EBNFRuleElement> rules = assignment.getRules().getRuleElementList();
            for (EBNFRuleElement rule : rules) {
                PsiElement child = rule.getFirstChild();
                if (child instanceof EBNFIdentifier) {
                    PsiElement prevSibling = rule.getPrevSibling();
                    if (prevSibling instanceof PsiWhiteSpace) {
                        prevSibling = prevSibling.getPrevSibling();
                    }
                    String token = prevSibling.getText();
                    if (token.equals("|") || token.equals("=")) {
                        identifiers.add(rule.getText());
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

}
