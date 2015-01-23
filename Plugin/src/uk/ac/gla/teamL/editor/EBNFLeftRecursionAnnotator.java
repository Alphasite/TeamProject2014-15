package uk.ac.gla.teamL.editor;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.parser.psi.EBNFAssignment;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;
import uk.ac.gla.teamL.parser.psi.EBNFRuleElement;
import uk.ac.gla.teamL.parser.psi.impl.EBNFIdentifierImpl;

import javax.naming.Reference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class EBNFLeftRecursionAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof EBNFAssignment) {
            EBNFAssignment assignment = (EBNFAssignment) psiElement;

            if (isLeftRecursive(assignment)) {
                annotationHolder.createWarningAnnotation(assignment, "Rule is left recursive: May cause issues with certain parser generators.");
            }
        }
    }

    private boolean isLeftRecursive(EBNFAssignment assignment) {

        String name = assignment.getName();

        Stack<EBNFAssignment> stack = new Stack<EBNFAssignment>();
        stack.push(assignment);

        while (!stack.empty()) {
            EBNFAssignment poppedAssignment = stack.pop();
//           try {
            ArrayList<String> identifiers = getIdentifiers(poppedAssignment);

            for (String identifier: identifiers) {
                if (identifier.equals(name)) {
                    stack.clear();
                    return true;
                } else {
                    EBNFAssignment idAssignment = getAssignment(identifier, poppedAssignment);
                    if (idAssignment != null) {
                        stack.push(idAssignment);
                    }
                }
            }
//           } catch (NullPointerException e) {
//               System.err.println("--------------------------------------------------");
//               System.err.println("NUll POINTER EXCEPTION");
//               System.err.println("ASSIGNMENT == NULL?");
//               if (poppedAssignment == null) {
//                   System.err.println("TRUE");
//               } else System.err.println("FALSE");
//               System.err.println("--------------------------------------------------");
//           }
        }
        PsiTreeUtil.getChildrenOfType(assignment, EBNFIdentifier.class);
        return false;
    }

    private ArrayList<String> getIdentifiers(EBNFAssignment assignment) {
        ArrayList<String> identifiers = new ArrayList<String>();

//        try {
            List<EBNFRuleElement> rules = assignment.getRuleElementList();
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
//        } catch (NullPointerException e) {
//            System.err.println("--------------------------------------------------");
//            System.err.println("NUll POINTER EXCEPTION");
//            System.err.println("ASSIGNMENT-> " + assignment.getName());
//            System.err.println("--------------------------------------------------");
//        }
        return identifiers;
    }

    private EBNFAssignment getAssignment(String identifier, EBNFAssignment assignment) {
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

//    private boolean isLeftRecursive(String name, EBNFAssignment assignment) {
//        ArrayList<String> identifiers = getIdentifiers(assignment);
//
//        boolean recursive = false;
//        for (String identifier: identifiers) {
//            if (identifier.equals(name)) {
//                recursive = true;
//            } else {
//                EBNFAssignment idAssignment = getAssignment(identifier, assignment);
//                if (idAssignment == null) { System.out.println("--> BREAK"); break; }
//                if (isLeftRecursive(name, idAssignment)) {
//                    recursive = true;
//                }
//            }
//        }
//        return recursive;
//    }
