package uk.ac.gla.teamL.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import uk.ac.gla.teamL.EBNFUtil;
import uk.ac.gla.teamL.psi.*;

/**
 * User: nishad
 * Date: 24/11/14
 * Time: 23:35
 */
public class EBNFParserImplUtil {
    @NotNull
    public static String getName(EBNFIdentifier id) {
        return id == null? "" : id.getText();
    }

    public static PsiElement setName(EBNFIdentifier id, @NonNls @NotNull String newName) throws IncorrectOperationException {
        ((EBNFIdentifierImpl) id).getId().replace(EBNFUtil.createElement(id.getProject(), newName));
        return id;
    }

    @NotNull
    public static String getName(EBNFAssignmentImpl element) {
        return element.getId().getName();
    }

    public static PsiElement setName(EBNFAssignmentImpl element, @NonNls @NotNull String name) throws IncorrectOperationException {
        element.getId().setName(name);
        return element;
    }

    public static PsiElement getNameIdentifier(EBNFAssignmentImpl element) {
        return element.getId();
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

    public static PsiReference getReference (EBNFIdentifier identifier) {

        // Assignments cannot be references, they are the reference base.
        if (identifier.getParent() instanceof EBNFAssignment) {
            return null;
        } else {
            return new EBNFIdentifierReferenceImpl<EBNFNamedElement>(identifier, identifier.getTextRange());
        }
    }

    private boolean hasReference(EBNFIdentifier identifier, @NotNull final PsiElement element, @NotNull final Class<? extends PsiReference> referenceClass) {
        for (PsiReference reference : element.getReferences()) {
            if (reference.getClass().equals(referenceClass)) {
                return true;
            }
        }
        return false;
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
}
