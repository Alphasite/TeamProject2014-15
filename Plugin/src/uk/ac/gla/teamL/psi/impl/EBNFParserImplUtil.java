package uk.ac.gla.teamL.psi.impl;

import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFIcon;
import uk.ac.gla.teamL.EBNFReference;
import uk.ac.gla.teamL.psi.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: nishad
 * Date: 24/11/14
 * Time: 23:35
 */
public class EBNFParserImplUtil {

    @NotNull
    public static String getName(EBNFAssignment element) {
        return element.getId().getText();
    }

    @NotNull
    public static PsiElement setName(EBNFAssignment element, String newName) {
        element.getId().setName(newName);
        return element;
    }

    @NotNull
    public static PsiElement getNameIdentifier(EBNFAssignment element) {
        return element.getId().getId();
    }

    @NotNull
    public static String getName(EBNFIdentifier element) {
        return element.getId().getText();
    }

    @NotNull
    public static PsiElement setName(EBNFIdentifier element, String newName) {
        EBNFIdentifier identifier = EBNFElementFactory.createIdentifier(element.getProject(), newName);
        element.replace(identifier);

        return element;
    }

    @NotNull
    public static PsiElement getNameIdentifier(EBNFIdentifier element) {
        return element.getId();
    }

    @NotNull
    public static PsiReference getReference(EBNFIdentifier identifier) {
        return new EBNFReference(identifier, new TextRange(0, identifier.getName().length()));
    }

    @NotNull
    public static String getString(EBNFString string) {
        PsiElement stringNode;

        if ((stringNode = string.getStringDoubleQuotes()) != null) {
            String toString = stringNode.getText();
//            toString = toString.replace("\\" , "");
            return toString.substring(1, toString.length() - 1);

        } else if ((stringNode = string.getStringSingleQuotes()) != null) {
            String toString = stringNode.getText();
//            toString = toString.replace("\\", "");
            return toString.substring(1, toString.length() - 1);

        } else if ((stringNode = string.getStringTripleQuotes()) != null) {
            String toString = stringNode.getText();
//            toString = toString.replace("\\", "");
            return toString.substring(3, toString.length() - 3);

        } else {
            return "";
        }
    }

    public static String getName(EBNFAnnotation annotation) {
        return annotation.getId().getText();
    }

    public static int getValue(EBNFNum num) {
        try {
            return Integer.parseInt(num.getNumbers().getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public static String toString(EBNFIdentifier id) {
        return "Identifier: " + id.getName();
    }

    public static String toString(EBNFAssignment assignment) {
        return "Assignment: " + assignment.getName();
    }

    public static ItemPresentation getPresentation(final EBNFAssignment element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile() != null? element.getContainingFile().getName() : null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return EBNFIcon.FILE;
            }
        };
    }

    @NotNull
    public static List<List<EBNFRuleElement>> getRuleSegmentList(EBNFRules rules) {
        List<List<EBNFRuleElement>> segments = new ArrayList<>();
        PsiElement child = rules.getFirstChild();

        List<EBNFRuleElement> segment = new ArrayList<>();
        for (EBNFRuleElement ruleElement : rules.getRuleElementList()) {
            segment.add(ruleElement);

            PsiElement nextSibling = ruleElement.getNextSibling();
            if (nextSibling instanceof PsiWhiteSpace) {
                nextSibling = nextSibling.getNextSibling();
            }

            if (nextSibling instanceof EBNFOr) {
                segments.add(segment);
                segment = new ArrayList<>();
            }
        }



        segments.add(segment);

        return segments;
    }
}
