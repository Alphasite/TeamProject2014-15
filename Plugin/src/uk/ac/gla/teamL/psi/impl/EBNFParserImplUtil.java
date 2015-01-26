package uk.ac.gla.teamL.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.psi.*;

/**
 * User: nishad
 * Date: 24/11/14
 * Time: 23:35
 */
public class EBNFParserImplUtil {

    public static String getName(EBNFAssignment element) {
        return element.getId().getText();
    }

    public static String getName(EBNFIdentifier element) {
        return element.getId().getText();
    }

    public static PsiElement setName(EBNFIdentifier element, String newName) {
        ASTNode oldNode = element.getNode().findChildByType(EBNFTypes.ID);

        if (oldNode != null) {
            EBNFIdentifier identifier = EBNFElementFactory.createIdentifier(element.getProject(), newName);

            element.getNode().replaceChild(oldNode, (ASTNode) identifier);
        }

        return element;
    }

    public static PsiElement getNameIdentifier(EBNFIdentifier element) {
        ASTNode node = element.getNode().findChildByType(EBNFTypes.ID);
        if (node != null) {
            return node.getPsi();
        } else {
            return null;
        }
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

        } else  if((stringNode = string.getStringTripleQuotes()) != null) {
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
}
