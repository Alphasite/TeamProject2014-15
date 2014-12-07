package uk.ac.gla.teamL.parser.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.ac.gla.teamL.EBNFUtil;
import uk.ac.gla.teamL.parser.psi.EBNFCompositeElement;
import uk.ac.gla.teamL.parser.psi.EBNFIdentifier;
import uk.ac.gla.teamL.parser.psi.EBNFString;

/**
 * User: nishad
 * Date: 24/11/14
 * Time: 23:35
 */
public class EBNFParserImplUtil {
    @Nullable
    @NonNls
    public static String getName(EBNFIdentifier id) {
        return id != null ? id.getText() : null;
    }

    public static PsiElement setName(EBNFIdentifier id, @NonNls @NotNull String newName) throws IncorrectOperationException {
        id.getId().replace(EBNFUtil.createElement(id.getProject(), newName));
        return id;
    }

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

    public static String getString(EBNFString string) {
        PsiElement stringNode;

        if ((stringNode = string.getStringDoubleQuotes()) != null) {
            String toString = stringNode.getText();
            toString = toString.replace("\\" , "");
            return toString.substring(1, toString.length() - 1);

        } else if ((stringNode = string.getStringSingleQuotes()) != null) {
            String toString = stringNode.getText();
            toString = toString.replace("\\", "");
            return toString.substring(1, toString.length() - 1);

        } else  if((stringNode = string.getStringTripleQuotes()) != null) {
            String toString = stringNode.getText();
            toString = toString.replace("\\", "");
            return toString.substring(3, toString.length() - 3);

        } else {
            return "";
        }
    }

    public static PsiReference resolveReference (EBNFIdentifier identifier) {
        return new EBNFReferenceImpl<EBNFCompositeElement>(identifier, identifier.getTextRange()) {
            @Override
            public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
                ((EBNFIdentifier) myElement).setName(newElementName);
                return myElement;
            }
        };
    }
}
